package com.dkom.atm.controller;
import com.dkom.atm.entity.PaymentCard;
import com.dkom.atm.entity.request.DataTransaction;
import com.dkom.atm.entity.request.NamePassword;
import com.dkom.atm.entity.request.PaymentCardRequest;
import com.dkom.atm.repository.PaymentCardRepository;
import com.dkom.atm.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CardController {

   CardService cardService;

   @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(value = "/newCard")
    @ResponseBody
    public String createNewCard(@RequestBody PaymentCardRequest paymentCardRequest){

        cardService.createNewCard(paymentCardRequest);
        return "create successfully";
    }

    @RequestMapping(value = "/getCards")
    @ResponseBody
    public List<PaymentCard> getAllCards(){

        return cardService.getListOfCards();

    }

    @RequestMapping(value = "/authentication")
    @ResponseBody
    public String cardAuthentication(@RequestBody NamePassword namePassword){
        String responseCode = cardService.cardAuthentication(namePassword.getName(), namePassword.getPassword());
        return responseCode;

    }

    @RequestMapping(value = "/transaction")
    @ResponseBody
    public PaymentCard moneyTransaction(@RequestBody DataTransaction dataTransaction) {

       return cardService.moneyTransaction(dataTransaction);

    }



}
