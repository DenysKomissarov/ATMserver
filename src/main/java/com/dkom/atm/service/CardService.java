package com.dkom.atm.service;

import com.dkom.atm.entity.PaymentCard;
import com.dkom.atm.dto.DataTransaction;
import com.dkom.atm.dto.PaymentCardRequest;

import java.util.List;

public interface CardService {
    void  createNewCard(PaymentCardRequest paymentCardRequest);
    String cardAuthentication(String name, String password);
    PaymentCard moneyTransaction(DataTransaction dataTransaction);
    List<PaymentCard> getListOfCards();
}
