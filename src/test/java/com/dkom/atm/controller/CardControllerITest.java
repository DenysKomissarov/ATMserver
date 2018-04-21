package com.dkom.atm.controller;

import com.dkom.atm.AtmApplication;
import com.dkom.atm.configuration.DatabaseConfigTest;
import com.dkom.atm.dto.ClientAuthentication;
import com.dkom.atm.dto.DataTransaction;
import com.dkom.atm.dto.PaymentCardRequest;
import com.dkom.atm.entity.PaymentCard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AtmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(DatabaseConfigTest.class)
public class CardControllerITest {

    private TestRestTemplate testRestTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void createNewCard() {
        HttpEntity<PaymentCardRequest> entity = new HttpEntity<>(paymentCardRequestCreate("0000111122223333"), headers);

        ResponseEntity<PaymentCard> responseEntity = testRestTemplate.exchange(createURLWithPort("/newCard"), HttpMethod.POST,
                entity, PaymentCard.class);

        assertEquals(responseEntity.getBody().getName(), "Vasiliy");
        assertEquals(responseEntity.getBody().getCardNumber(), "0000111122223333");
        assertEquals(responseEntity.getBody().getBirthday(), "03.03.2003");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        entity = new HttpEntity<>(paymentCardRequestCreate("1111222233334444"), headers);

        responseEntity = testRestTemplate.exchange(createURLWithPort("/newCard"), HttpMethod.POST,
                entity, PaymentCard.class);

        assertEquals(responseEntity.getBody().getName(), "Vasiliy");
        assertEquals(responseEntity.getBody().getCardNumber(), "1111222233334444");
        assertEquals(responseEntity.getBody().getBirthday(), "03.03.2003");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void getAllCards()  {
        createNewCard();
        ResponseEntity<List<PaymentCard>> responseEntity = testRestTemplate.exchange(createURLWithPort("/getCards"), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<PaymentCard>>() {});
        List<PaymentCard> actualList = responseEntity.getBody();

        assertThat(actualList.size(),is(4));
    }


    @Test
    public void cardAuthentication() {
        createNewCard();
        HttpEntity<ClientAuthentication> entity = new HttpEntity<>(clientAuthenticationCreate("0000111122223333", "7777"), headers);

        ResponseEntity<String> responseEntity = testRestTemplate.exchange(createURLWithPort("/authentication"), HttpMethod.POST,
                entity, String.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);


    }

    @Test
    public void moneyTransaction() {
        createNewCard();
        HttpEntity<DataTransaction> entity = new HttpEntity<>(dataTransactionCreate("0000111122223333",
                "1111222233334444", "7777", 1000), headers);

        ResponseEntity<PaymentCard> responseEntity = testRestTemplate.exchange(createURLWithPort("/transaction"), HttpMethod.POST,
                entity, PaymentCard.class);

        assertEquals(responseEntity.getBody().getName(), "Vasiliy");
        assertEquals(responseEntity.getBody().getCardNumber(), "0000111122223333");
        assertEquals(responseEntity.getBody().getBirthday(), "03.03.2003");

        assertEquals(responseEntity.getBody().getBalance(), 9000, 0.0);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

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

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}