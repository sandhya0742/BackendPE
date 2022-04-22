package com.arshaa.service;

import com.arshaa.common.Bed;
import com.arshaa.common.Payment;
import com.arshaa.entity.Guest;
import com.arshaa.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class GuestService implements GuestInterface {
    @Autowired(required = true)
    private GuestRepository repository;

    @Autowired
    @Lazy
    private RestTemplate template;
    
    @Autowired
	@PersistenceContext
	private EntityManager em;

    @Override
    public List<Guest> getGuests() {
        return repository.findAll();
    }

    @Override
    public Guest getGuestById(String guestId) {
        return repository.findById(guestId);
    }

    @Override
    public Guest addGuest(Guest guest) {
        //double initialDefaultrent = 0;
        String bedUri = "http://bedService/bed/updateBedStatusBydBedId";
        String payUri = "http://paymentService/payment/addPaymentAtOnBoarding";
 //     Bed getUniqueBed = template.getForObject("http://bedService/bed/getBedByBedId/" + guest.getBedId(), Bed.class);
//        if (getUniqueBed.getBedId().equalsIgnoreCase(guest.getBedId())) {
//            System.out.println(getUniqueBed.getBedId());
//            guest.setDueAmount(getUniqueBed.getDefaultRent() - guest.getAmountPaid());
//        }
        java.sql.Date tSqlDate = new java.sql.Date(guest.getTransactionDate().getTime());
        guest.setTransactionDate(tSqlDate);
        java.sql.Date cSqlDate = new java.sql.Date(guest.getCheckInDate().getTime());
        guest.setCheckInDate(cSqlDate);
        if(guest.getOccupancyType().equalsIgnoreCase("daily"))
        {
        	java.util.Date m = guest.getCheckInDate();
            Calendar cal = Calendar.getInstance();  
            cal.setTime(m);  
            cal.add(Calendar.DATE, guest.getDuration()); 
            m = cal.getTime();   
            System.out.println(m);
            guest.setCheckOutDate(m);
            guest.setGuestStatus(true);            
            repository.save(guest);
        }
        else if(guest.getOccupancyType().equalsIgnoreCase("monthly"))
        {
        	java.util.Date m = guest.getCheckInDate();
            Calendar cal = Calendar.getInstance();  
            cal.setTime(m);  
            cal.add(Calendar.MONTH, guest.getDuration()); 
            m = cal.getTime();   
            System.out.println(m);
            guest.setCheckOutDate(m);
            guest.setGuestStatus(true);            
            repository.save(guest);
        }        
        else {
            guest.setGuestStatus(true);            

            repository.save(guest);
        }


//        System.out.println(initialDefaultrent); 
        guest.setGuestStatus(true);            

        repository.save(guest);
                System.out.println(guest.getDueAmount());
        Bed bedReq = new Bed();
        Payment payReq = new Payment();
        //bed setting
        bedReq.setBedId(guest.getBedId());
        
        bedReq.setGuestId(guest.getId());
        //bedReq.setDueAmount(guest.getDueAmount());
        template.put(bedUri, bedReq, Bed.class);
        //payment setting
        payReq.setGuestId(guest.getId());
        payReq.setTransactionId(guest.getTransactionId());
        payReq.setOccupancyType(guest.getOccupancyType());
        payReq.setTransactionDate(tSqlDate);
        payReq.setCheckinDate(cSqlDate);
        payReq.setAmountPaid(guest.getAmountPaid());
        payReq.setDueAmount(guest.getDueAmount());
        //payReq.setPaymentPurpose(guest.getPaymentPurpose());
        Payment parRes = template.postForObject(payUri, payReq, Payment.class);
        System.out.println(parRes);
                return guest;
    }

    @Override
    public double updateGuest(Guest guest) {
        Guest newGuest = repository.findById(guest.getId());
        newGuest.setDueAmount(guest.getDueAmount());
        repository.save(newGuest);
        return newGuest.getDueAmount();
    }

    @Override
    public void deleteGuest(String guestId) {
        Guest deleteGuest = repository.findById(guestId);
        repository.delete(deleteGuest);
    }
    
 // Method to fetch the dueamount by guestId .
 	@SuppressWarnings("unchecked")
 	@Override
 	public List<Guest> getByGuestId(String guestId) {
 		// TODO Auto-generated method stub

 		return em.createNamedStoredProcedureQuery("firstProcedure").setParameter("g_id", guestId).getResultList();

 	}

      //Method to fetch all the dueamount .
 	@SuppressWarnings("unchecked")
 	@Override
 	public List<Guest> getTotalDue() {

 		return em.createNamedStoredProcedureQuery("secondProcedure").getResultList();

 	}

	@Override
	public List<Guest> getPendingByBuildingId(int buildingId) {
		// TODO Auto-generated method stub
		return em.createNamedStoredProcedureQuery("thirdProcedure").setParameter("b_id", buildingId).getResultList();


	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Guest> getCheckOutAmountByGuestId(String id) {
	// TODO Auto-generated method stub
	return em.createNamedStoredProcedureQuery("checkOut").setParameter("GUEST__ID" , id).getResultList();
	}

	@Override
	public List<Guest> getFinalDueAmountById(String id) {
		// TODO Auto-generated method stub
		return em.createNamedStoredProcedureQuery("finalDue").setParameter("GUEST__ID" , id).getResultList();
	}



}
