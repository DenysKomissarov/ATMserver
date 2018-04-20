package com.dkom.atm.controller;

import com.dkom.atm.configuration.MockDependencies;
import com.dkom.atm.dto.ClientAuthentication;
import com.dkom.atm.dto.DataTransaction;
import com.dkom.atm.dto.PaymentCardRequest;
import com.dkom.atm.service.CardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.plugin2.util.PojoUtil.toJson;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MockDependencies.class})
public class CardControllerValidationTest {

    @Mock
    private CardService cardService;

    @InjectMocks
    CardController cardController;

    @Autowired
    MockMvc mvc;

    @Test(expected = NestedServletException.class)
    public void createNewCard() throws Exception {

        PaymentCardRequest paymentCardRequest = paymentCardRequestCreate("0000111122223333");

            MvcResult result = mvc.perform(post("/newCard").
            contentType(MediaType.APPLICATION_JSON).
            content(toJson(paymentCardRequest))).
            andExpect(status().isOk()).
            andReturn();
    }

    @Test(expected = NestedServletException.class)
    public void cardAuthentication() throws Exception {
        ClientAuthentication clientAuthentication = clientAuthenticationCreate("0000111122223333", "7777");

            MvcResult result = mvc.perform(post("/authentication").
                    contentType(MediaType.APPLICATION_JSON).
                    content(toJson(clientAuthentication))).
                    andExpect(status().isOk()).
                    andReturn();


    }

    @Test(expected = NestedServletException.class)
    public void moneyTransaction() throws Exception {
        DataTransaction dataTransaction = dataTransactionCreate("0000111122223333", "1111222233334444",
                "7777", 1000);

            MvcResult result = mvc.perform(post("/authentication").
                    contentType(MediaType.APPLICATION_JSON).
                    content(toJson(dataTransaction))).
                    andExpect(status().isOk()).
                    andReturn();

    }


    private PaymentCardRequest paymentCardRequestCreate(String numberOfCard){
        PaymentCardRequest paymentCardRequest = new PaymentCardRequest();
        paymentCardRequest.setName("Vasiliy");
        paymentCardRequest.setCardNumber(numberOfCard);
        paymentCardRequest.setBirthday("03.03.2003");
        paymentCardRequest.setSex("male");
        paymentCardRequest.setAddress("zp");
        return paymentCardRequest;
    }

    private DataTransaction dataTransactionCreate(String cardNumberSender, String cardNumberDestination, String password, float tranzactionAmount){
        DataTransaction dataTransaction = new DataTransaction();
        dataTransaction.setNumberDestination(cardNumberDestination);
        dataTransaction.setNumberSender(cardNumberSender);
        dataTransaction.setPassword(password);
        dataTransaction.setTranzactionAmount(tranzactionAmount);
        return dataTransaction;
    }

    private ClientAuthentication clientAuthenticationCreate(String cardNumber, String password){
        ClientAuthentication clientAuthentication = new ClientAuthentication();
        clientAuthentication.setCardNumber(cardNumber);
        clientAuthentication.setPassword(password);
        return clientAuthentication;
    }
}