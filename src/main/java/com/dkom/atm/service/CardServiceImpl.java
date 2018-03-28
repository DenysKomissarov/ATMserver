package com.dkom.atm.service;

import com.dkom.atm.entity.PaymentCard;
import com.dkom.atm.dto.DataTransaction;
import com.dkom.atm.dto.PaymentCardRequest;
import com.dkom.atm.repository.PaymentCardRepository;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

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
        paymentCard.setPassword("7777");
        PaymentCard paymentCardResponce = paymentCardRepository.save(paymentCard);

        logger.info("Thread : "+Thread.currentThread());
        return  paymentCardResponce;
    }

    @Override
    @Transactional
    public ResponseEntity<String> cardAuthentication(String number, String password) {

        List<PaymentCard> paymentCardList = paymentCardRepository.findCardsByCardNumber(number);
        logger.info("Size : "+paymentCardList.size());
        if (paymentCardList.size() == 0){
            throw new ServiceException("UnauthorizedException");
        }
        else if(!paymentCardList.get(0).getPassword().equals(password)){
            throw new ServiceException("UnauthorizedException");
        }
        else{
            return new ResponseEntity<>(HttpStatus.valueOf(200));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<PaymentCard> moneyTransaction(DataTransaction dataTransaction) {

        cardAuthentication(dataTransaction.getNumberSender(), dataTransaction.getPassword());

        List<PaymentCard> paymentCardListSender = paymentCardRepository.findCardsByCardNumber(dataTransaction.getNumberSender());

        List<PaymentCard> paymentCardListDestination = paymentCardRepository.findCardsByCardNumber(dataTransaction.getNumberDestination());

        if (paymentCardListSender.get(0).getBalance() < dataTransaction.getTranzactionAmount()){
            throw new NotAcceptableStatusException("not acceptable");
        }

        float summ = paymentCardListSender.get(0).getBalance() - dataTransaction.getTranzactionAmount();
        paymentCardRepository.updateBalance(summ, dataTransaction.getNumberSender());

        summ = paymentCardListDestination.get(0).getBalance() + dataTransaction.getTranzactionAmount();
        paymentCardRepository.updateBalance(summ, dataTransaction.getNumberDestination());

        paymentCardListSender = paymentCardRepository.findCardsByCardNumber(dataTransaction.getNumberSender());

        return new ResponseEntity<>(paymentCardListSender.get(0), HttpStatus.valueOf(200)) ;
    }

    @Override
    @Transactional
    public List<PaymentCard> getListOfCards() {

        return paymentCardRepository.findAll(Sort.by(Sort.Order.asc("cardNumber")));
    }


}
