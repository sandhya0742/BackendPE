package com.payment.service;

import com.payment.common.Guest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.payment.entity.Payments;
import com.payment.repos.PayRepos;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentImplement implements PaymentService {

    // Autowiring the Payment Repository extending JPA Repository .
    @Autowired
    private PayRepos repo;

    @Autowired
    @Lazy
    private RestTemplate template;

    Logger log = LoggerFactory.getLogger(PaymentImplement.class);

    //1.Method to fetching the List<>  payment details .
    @Override
    public List<Payments> getPayment() {
        return repo.findAll();
    }

    //2.Method to fetch payment details of one Particular Guest By PaymentId .
    @Override
    public Payments getPaymentById(int paymentId) {
        // TODO Auto-generated method stub

        return repo.findById(paymentId).orElse(null);
    }

    //3.Method to Updating data of payment history by manager .
    @Override
    public Payments updatePayment(Payments payment) {
        // TODO Auto-generated method stub
        Payments pay = repo.findById(payment.getPaymentId()).orElse(null);
        pay.setAmountPaid(payment.getAmountPaid());
        // pay.setDefaultRent(payment.getDefaultRent());
        pay.setDueAmount(payment.getDueAmount());
        //pay.setDefaultRent(payment.getDefaultRent());

        //pay.setPaymentMethod(payment.getPaymentMethod());
//		pay.setSecurityDeposit(payment.getSecurityDeposit());
        //pay.setNewDuesAmount(payment.getNewDuesAmount());
        return repo.save(pay);
    }

    //4.Method to Call at the time when user is OnBoarding .
    @Override
    public Payments addPayment(Payments payment) {
        // TODO Auto-generated method stub
        Payments firstPay = new Payments();
        try {
            firstPay.setTransactionId(payment.getTransactionId());
            firstPay.setGuestId(payment.getGuestId());
            firstPay.setOccupancyType(payment.getOccupancyType());
            firstPay.setTransactionDate(payment.getTransactionDate());
            firstPay.setAmountPaid(payment.getAmountPaid());
            if (firstPay.getAmountPaid() > 0) {
                firstPay.setOnBoard(true);
            }
            firstPay.setDueAmount(payment.getDueAmount());
            firstPay.setCheckinDate(payment.getCheckinDate());
            //firstPay.setPaymentPurpose(payment.getPaymentPurpose());
            return repo.save(firstPay);
        } catch (Exception e) {
            e.printStackTrace();
        }

//		int remainder = payment.getRemainder();
//		firstPay.setRemainder(remainder);
//
//		double newDuesAmount = payment.getNewDuesAmount();
//		firstPay.setNewDuesAmount(newDuesAmount);
//		double count = securityDeposit + defaultRent;
//
//		/* 1.first dueAmount will be calculated as   dueAmount = count = securityDeposit + roomRent;
//		 * If remainder days is less than 30 and greater than 1 then dueAmount will store as :dueAmount =(roomRent / 30) *remainder;
//		 */
//		if (remainder < 30 && remainder > 1) {
//
//			dueAmount = (defaultRent / 30) * remainder;
//			dueAmount = count + dueAmount;
//		} else if (remainder == 30) {
//			dueAmount = securityDeposit + defaultRent;
//		}
//		/*
//		 * If guest will onboard then a new dues amount will be calcualated as follows .
//		 */
//		if (onBoard == true) {
//			payment.setDueAmount(dueAmount);
//			newDuesAmount = dueAmount - amountPaid;
//			payment.setNewDuesAmount(newDuesAmount);
//		}

        return null;
    }


    public Payments getPaymentByGuestId(String guestId) {
        Payments responsePay = repo.findByGuestId(guestId);
        return responsePay;
    }

    //6.Getting the dues Of particular Guest based on their id .
    @Override
    public double getDueAmountByGuestId(String guestId) {
        // TODO Auto-generated method stub
        return 0;
    }

    //7.Posting the data of Guest After onBOarding .
    @Override
    public Payments addPaymentAfterOnBoard(Payments payment) {
        String uri = "http://guestService/guest/updateDueAmount";
        Guest guest = new Guest();
        Payments secondpay = new Payments();
        try {
           Payments due = repo.findDueAmountByGuestId(payment.getGuestId());
            secondpay.setAmountPaid(payment.getAmountPaid());
//            secondpay.setDueAmount(payment.getDueAmount()-payment.getAmountPaid());
            secondpay.setDueAmount(due.getDueAmount()- payment.getAmountPaid());
            //secondpay.setDueAmount(payment.getDueAmount());
            secondpay.setTransactionId(payment.getTransactionId());
            secondpay.setPaymentPurpose(payment.getPaymentPurpose());
            secondpay.setGuestId(payment.getGuestId());
            java.sql.Date tSqlDate = new java.sql.Date(payment.getTransactionDate().getTime());
            payment.setTransactionDate(tSqlDate);
            secondpay.setTransactionDate(payment.getTransactionDate());
            repo.save(secondpay);
            guest.setId(secondpay.getGuestId());
            guest.setDueAmount(secondpay.getDueAmount());
            template.put(uri, guest, Guest.class);
            return secondpay;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
