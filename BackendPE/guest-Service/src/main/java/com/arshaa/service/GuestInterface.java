package com.arshaa.service;

import com.arshaa.common.Bed;
import com.arshaa.entity.Guest;

import java.net.URISyntaxException;
import java.util.List;

public interface GuestInterface {

    public List<Guest> getGuests();

    public Guest getGuestById(String guestId);

    public Guest addGuest(Guest guest) ;

    public double updateGuest(Guest guest);

    public void deleteGuest(String guestId);
    public List<Guest> getByGuestId(String guestId) ;
    
    public List<Guest>  getTotalDue();
    
    public List<Guest> getPendingByBuildingId(int buildingId);
}
