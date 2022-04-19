package com.payment.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.payment.common.DuePieChart;
import com.payment.common.PendingPayments;
import com.payment.common.RecentTransactions;
import com.payment.repos.PayRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.payment.entity.Payments;
import com.payment.service.PaymentService;

@RestController
@CrossOrigin("*")
@RequestMapping("/payment")
public class PayCOntroller {

	@Autowired
	private PaymentService serve;

	@Autowired
	private PayRepos repos;

	// http://localhost:8989/payment/addPaymentAtOnBoarding
//ADDING PAYMENT AT ONBOARDING TIME .
	@PostMapping("/addPaymentAtOnBoarding")
	public Payments addPayment(@RequestBody Payments payment) {
		return this.serve.addPayment(payment);
	}

	// http://localhost:8989/payment/updatePaymentByPaymentId/{paymentId}
//UPDATING PAYMENT BASED ON PAYMENT ID.
	@PutMapping("/updatePaymentByPaymentId/{paymentId}")
	public Payments updatePayment(@RequestBody Payments payment) {
		return this.serve.updatePayment(payment);
	}

	// http://localhost:8989/payment/getPaymentDetail/{paymentId}
//RETRIEVE PAYMENT DETAILS BASED ON PAYMENT ID .
	@GetMapping("/getPaymentDetail/{paymentId}")
	public Payments getPaymentById(@PathVariable int paymentId) {
		return this.serve.getPaymentById(paymentId);
	}

	// http://localhost:8989/payment/getAllPayments
//RETRIEVE ALL PAYMENTS AS LIST .
	@GetMapping("/getAllPayments")
	public List<Payments> getPayment() {
		return this.serve.getPayment();
	}

	// GET THE TRANSACTION HISTORY BASED ON GUESTID .
	@GetMapping("/getPaymentByGuestId/{guestId}")
	public Payments findByGuestId(@PathVariable String guestId) {
		return this.serve.getPaymentByGuestId(guestId);
	}

	// http://localhost:8989/api/getDuesAmounts/{guestId}
//FETCHING DUE AMOUNT BASED ON GUESTID .
	@GetMapping("/getDuesAmounts/{guestId}")
	public double getDueAmountByGuestId(String guestId) {
		return this.serve.getDueAmountByGuestId(guestId);
	}

	// POSTING INFORMATION OF PAYMENT BASED ON GUEST TYPE .
	@PostMapping("/addAfterOnBoard")
	public Payments addPaymentAfterOnBoar(@RequestBody Payments payment) {
		return this.serve.addPaymentAfterOnBoard(payment);
	}

	@GetMapping("/getTrasactionHistoryByGuestId/{guestId}")
	public List<Payments> findTransactionsByGuestId(@PathVariable String guestId) {
		return repos.findAllPaymentsByGuestId(guestId);
	}

	@GetMapping("/getRecentPayments")
	public List<RecentTransactions> getResentTransactions(@RequestParam String field) {
		List<RecentTransactions> recent = new ArrayList<>();
		if (field.equals("date")) {
			Optional<List<Payments>> pay = repos.findTop30AllByOrderByTransactionDateDesc();
			if (pay.isPresent()) {
				pay.get().forEach(payment -> {
					RecentTransactions rt = new RecentTransactions();
					rt.setAmountPaid(payment.getAmountPaid());
					rt.setGuestId(payment.getGuestId());
					// rt.setPaymentId(payment.getPaymentId());
					rt.setId(payment.getPaymentId());
					rt.setPaymentPurpose(payment.getPaymentPurpose());
					rt.setTransactionDate(payment.getTransactionDate());
					rt.setTransactionId(payment.getTransactionId());
					recent.add(rt);
				});
			}
		}
		return recent;
	}

	/*
	 * @GetMapping("/pendingPayments") public PendingPayments getPendingPayments() {
	 * // TODO Auto-generated method stub //Payment
	 * getpayment=repos.findAllGuests(); Payments payments = new Payments();
	 * //Payment getGuests=repos.findByGuestId(payments.getGuestId());
	 * PendingPayments pp = new PendingPayments(); //List<Payment>
	 * pay=repos.findPaymentByGuestId(payments.getGuestId()); if
	 * (payments.getDueAmount() > 0) { } List<Payments> pay1 = repos.findAll(); int
	 * count = pay1.size(); pp.setPendingPayments(count); return pp; }
	 */
	@GetMapping("/pendingPayments")
	public PendingPayments getPendingPayments() {
		// TODO Auto-generated method stub
		// Payment getpayment=repos.findAllGuests();
		Payments payments = new Payments();
		// Payment getGuests=repos.findByGuestId(payments.getGuestId());
		PendingPayments pp = new PendingPayments();
		// List<Payment> pay=repos.findPaymentByGuestId(payments.getGuestId());
		List<Payments> p = repos.getDueAmountByGuestId(payments.getGuestId());

		if (p.isEmpty()) {
			p.forEach(pay -> {
				int count = p.size();
				if (pay.getDueAmount() > 0) {
					int c = p.size();
					pp.setPendingPayments(c);
				} else if (pay.getDueAmount() < 0) {
					int f = p.size();
				}
			});
		}
		// pp.setPendingPayments(c);
		return pp;

	}

	// API CALL FOR FETCHING OVERALL DUE AMOUNT

	@GetMapping("/fetchingOverAllDueAmount")
	public DuePieChart findTotalDue() {
		double pay = repos.getTotalDue();
		DuePieChart ddc = new DuePieChart();
		ddc.setOverAllDue(pay);
		return ddc;

	}

	// API CALL FOR FETCHING OVERALL DUE AMOUNT(ONLY COUNT)
	@GetMapping("/pendingPaymentCount")
	public PendingPayments getCount() {
		int pays = repos.getCount();
		PendingPayments ppt = new PendingPayments();
		ppt.setPendingPayments(pays);
		return ppt;
	}
//    @GetMapping("/getDueByGuestId/{guestId}")
//    public double getDueById(@PathVariable String guestId) {
//        return repos.findDueAmountByGuestId(guestId);
//    }

//    @GetMapping("/getLatestDueAmountByRecentDate/{guestId}")
//    public Payments getLatestDueAmountByRecentDate(@RequestParam String guestId){
//        return repos.findTop1ByTransactionDateAndGuestId(guestId);
//    }

//    @GetMapping("/getLastDueAmount/{guestId}")
//    public void getLastDueAmount(@PathVariable String guestId){
//       List<Payments> allPayments =  repos.findPaymentsByGuestId(guestId);
//        System.out.println(allPayments);
//       Date max  = (Date) allPayments.stream().map(Payments::getTransactionDate).max(Date::compareTo).get();
//    }
}
