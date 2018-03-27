package com.dkom.atm.service;

import com.dkom.atm.entity.PaymentCard;
import com.dkom.atm.dto.DataTransaction;
import com.dkom.atm.dto.PaymentCardRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CardService {
    PaymentCard  createNewCard(PaymentCardRequest paymentCardRequest);
    ResponseEntity<String> cardAuthentication(String name, String password);
    ResponseEntity<PaymentCard> moneyTransaction(DataTransaction dataTransaction);
    List<PaymentCard> getListOfCards();
}
