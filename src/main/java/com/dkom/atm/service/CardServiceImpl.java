package com.dkom.atm.service;

import com.dkom.atm.entity.PaymentCard;
import com.dkom.atm.dto.DataTransaction;
import com.dkom.atm.dto.PaymentCardRequest;
import com.dkom.atm.repository.PaymentCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private static final Logger logger = LoggerFactory.getLogger(CardService.class);
    private PaymentCardRepository paymentCardRepository;

    @Autowired
    public CardServiceImpl(PaymentCardRepository paymentCardRepository) {
        this.paymentCardRepository = paymentCardRepository;
    }

    @Override
    @Transactional
    public PaymentCard createNewCard(PaymentCardRequest paymentCardRequest) {
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setName(paymentCardRequest.getName());
        paymentCard.setCardNumber(paymentCardRequest.getCardNumber());
        paymentCard.setBirthday(paymentCardRequest.getBirthday());
        paymentCard.setSex(paymentCardRequest.getSex());
        paymentCard.setAddress(paymentCardRequest.getAddress());
        paymentCard.setBalance(10000);
        PaymentCard paymentCardResponce = paymentCardRepository.saveAndFlush(paymentCard);

        logger.info("id : "+paymentCardResponce.getId());
        return  paymentCardResponce;
    }

    @Override
    @Transactional
    public ResponseEntity<String> cardAuthentication(String number, String password) {

        List<PaymentCard> paymentCardList = paymentCardRepository.findCardsByCardNumber(number);
        if (paymentCardList.size() == 0){
            logger.info("authentication cliens count = 0");
            return new ResponseEntity<>(HttpStatus.valueOf(401));
        }
        else if(!paymentCardList.get(0).getPassword().equals(password)){
            logger.info("authentication wrong password");
            return new ResponseEntity<>(HttpStatus.valueOf(401));
        }
        else{
            logger.info("authentication ok");
            return new ResponseEntity<>(HttpStatus.valueOf(200));
        }

    }

    @Override
    @Transactional
    public ResponseEntity<PaymentCard> moneyTransaction(DataTransaction dataTransaction) {

        ResponseEntity<String> responseCode = cardAuthentication(dataTransaction.getNumberSender(), dataTransaction.getPassword());

        if (responseCode.getStatusCode() == HttpStatus.valueOf(200)){
            List<PaymentCard> paymentCardListSender = paymentCardRepository.findCardsByCardNumber(dataTransaction.getNumberSender());

            List<PaymentCard> paymentCardListDestination = paymentCardRepository.findCardsByCardNumber(dataTransaction.getNumberDestination());
            if (paymentCardListSender.size() == 0 || paymentCardListDestination.size() == 0){
                logger.info("Wrong data in DataTranzaction");
                return null;  // wrong authentication        // validation
            }
            if (paymentCardListSender.get(0).getBalance() < dataTransaction.getTranzactionAmount()){
                return new ResponseEntity<>(HttpStatus.valueOf(406));
            }

            float summ = paymentCardListSender.get(0).getBalance() - dataTransaction.getTranzactionAmount();
            paymentCardRepository.updateBalance(summ, dataTransaction.getNumberSender());

            paymentCardRepository.flush();

            summ = paymentCardListDestination.get(0).getBalance() + dataTransaction.getTranzactionAmount();
            paymentCardRepository.updateBalance(summ, dataTransaction.getNumberDestination());
            paymentCardRepository.flush();

            paymentCardListSender = paymentCardRepository.findCardsByCardNumber(dataTransaction.getNumberSender());

            return new ResponseEntity<>(paymentCardListSender.get(0), HttpStatus.valueOf(200)) ;
        }
        else{
            return new ResponseEntity<>(responseCode.getStatusCode());
        }
    }

    @Override
    public List<PaymentCard> getListOfCards() {

        return paymentCardRepository.findAll(Sort.by(Sort.Order.asc("cardNumber")));
    }


}
