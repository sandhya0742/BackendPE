package com.payment.repos;

import com.payment.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayRepos extends JpaRepository<Payments, Integer> {
    Payments findByGuestId(String guestId);

    Payments findDueAmountByGuestId(String guestId);

    //@Query()

    List<Payments> findPaymentsByGuestId(String guestId);

    @Query(value = "select sum(due_amount) from Payments ", nativeQuery = true)
    public double getTotalDue();


    @Query(value = "SELECT COUNT(*) FROM payments WHERE due_amount>0 ", nativeQuery = true)
    public int getCount();

    //List<Payment> findAllByOrderByTransactionDateDesc(Date transactionDate);
//List<Payment> findAll(Date transactionDate);
    Optional<List<Payments>> findTop30AllByOrderByTransactionDateDesc();

	List<Payments> findAllPaymentsByGuestId(String guestId);

	List<Payments> getDueAmountByGuestId(String guestId);
}

