package com.dkom.atm.service;

import com.dkom.atm.entity.PaymentCard;
import com.dkom.atm.dto.DataTransaction;
import com.dkom.atm.dto.PaymentCardRequest;
import com.dkom.atm.repository.PaymentCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    public void createNewCard(PaymentCardRequest paymentCardRequest) {
        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setName(paymentCardRequest.getName());
        paymentCard.setCardNumber(paymentCardRequest.getCardNumber());
        paymentCard.setBirthday(paymentCardRequest.getBirthday());
        paymentCard.setSex(paymentCardRequest.getSex());
        paymentCard.setAddress(paymentCardRequest.getAddress());
        paymentCard.setBalance(10000);
        paymentCardRepository.save(paymentCard);
    }

    @Override
    @Transactional
    public String cardAuthentication(String number, String password) {

        List<PaymentCard> paymentCardList = paymentCardRepository.findCardsByCardNumber(number);
        if (paymentCardList.size() == 0){
            return "401";
        }
        else if(!paymentCardList.get(0).getPassword().equals(password)){
            return "401";
        }
        return "200";
    }

    @Override
    @Transactional
    public PaymentCard moneyTransaction(DataTransaction dataTransaction) {
        String responseCode = cardAuthentication(dataTransaction.getNumberSender(), dataTransaction.getPassword());
        if (responseCode.equals("200")){
            List<PaymentCard> paymentCardListSender = paymentCardRepository.findCardsByCardNumber(dataTransaction.getNumberSender());

            List<PaymentCard> paymentCardListDestination = paymentCardRepository.findCardsByCardNumber(dataTransaction.getNumberDestination());
            if (paymentCardListSender.size() == 0 || paymentCardListDestination.size() == 0){
                logger.info("Wrong data in DataTranzaction");
                return null;  // wrong authentication
            }
            if (paymentCardListSender.get(0).getBalance() < dataTransaction.getTranzactionAmount()){
                return null; ////406
            }
            float summ = paymentCardListSender.get(0).getBalance() - dataTransaction.getTranzactionAmount();
            logger.info("summ of "+paymentCardListSender.get(0).getCardNumber()+" : " + summ);
            paymentCardRepository.updateBalance(summ, dataTransaction.getNumberSender());
            paymentCardRepository.flush();

            summ = paymentCardListDestination.get(0).getBalance() + dataTransaction.getTranzactionAmount();
            logger.info("summ of  "+paymentCardListSender.get(0).getCardNumber()+" : " + summ);
            paymentCardRepository.updateBalance(summ, dataTransaction.getNumberDestination());
            paymentCardRepository.flush();

            paymentCardListSender = paymentCardRepository.findCardsByCardNumber(dataTransaction.getNumberSender());

            return paymentCardListSender.get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public List<PaymentCard> getListOfCards() {

        return paymentCardRepository.findAll(Sort.by(Sort.Order.asc("cardNumber")));
    }


}
