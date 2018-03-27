package com.dkom.atm.repository;

import com.dkom.atm.entity.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {

    List<PaymentCard> findCardsByCardNumber(String number);
    List<PaymentCard> findCardsByPassword(String password);

    @Modifying
    @Query("update PaymentCard p set p.balance = ?1 where p.cardNumber = ?2")
    int updateBalance(float balance, String cardNumber);


}
