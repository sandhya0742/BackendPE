package com.arshaa.repository;

import com.arshaa.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {
    Guest findById(String guestId);

	Guest getGuestBybedId(String bedId);

	Guest getGuestBybedIdAndGuestStatus(String bedId, String guestStatus);



}
