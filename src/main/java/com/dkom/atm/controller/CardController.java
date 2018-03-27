package com.dkom.atm.controller;
import com.dkom.atm.entity.PaymentCard;
import com.dkom.atm.dto.DataTransaction;
import com.dkom.atm.dto.NamePassword;
import com.dkom.atm.dto.PaymentCardRequest;
import com.dkom.atm.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CardController {

   private CardService cardService;

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

        return cardService.cardAuthentication(namePassword.getName(), namePassword.getPassword());

    }

    @RequestMapping(value = "/transaction")
    @ResponseBody
    public PaymentCard moneyTransaction(@RequestBody DataTransaction dataTransaction) {

       return cardService.moneyTransaction(dataTransaction);

    }



}
