package com.dkom.atm.service;

import com.dkom.atm.entity.PaymentCard;
import com.dkom.atm.entity.request.DataTransaction;
import com.dkom.atm.entity.request.NamePassword;
import com.dkom.atm.entity.request.PaymentCardRequest;

import java.util.List;

public interface CardService {
    void  createNewCard(PaymentCardRequest paymentCardRequest);
    String cardAuthentication(String name, String password);
    PaymentCard moneyTransaction(DataTransaction dataTransaction);
    List<PaymentCard> getListOfCards();
}
