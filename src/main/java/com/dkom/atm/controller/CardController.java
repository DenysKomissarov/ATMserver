package com.dkom.atm.controller;
import com.dkom.atm.dto.ClientAuthentication;
import com.dkom.atm.entity.PaymentCard;
import com.dkom.atm.dto.DataTransaction;
import com.dkom.atm.dto.PaymentCardRequest;
import com.dkom.atm.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
   private CardService cardService;



    @RequestMapping(value = "/newCard")
    @ResponseBody
    public PaymentCard createNewCard(@Valid @RequestBody PaymentCardRequest paymentCardRequest){

        return cardService.createNewCard(paymentCardRequest);
    }

    @RequestMapping(value = "/authentication")
    @ResponseBody
    public ResponseEntity<String> cardAuthentication(@Valid @RequestBody ClientAuthentication clientAuthentication){

        return cardService.cardAuthentication(clientAuthentication.getCardNumber(), clientAuthentication.getPassword());

    }

    @RequestMapping(value = "/transaction")
    @ResponseBody
    public ResponseEntity<PaymentCard> moneyTransaction(@Valid @RequestBody DataTransaction dataTransaction) {

        return cardService.moneyTransaction(dataTransaction);

    }

    @RequestMapping(value = "/getCards")
    @ResponseBody
    public List<PaymentCard> getAllCards(){
        System.out.println("RequestMapping");
        return cardService.getListOfCards();

    }

}
