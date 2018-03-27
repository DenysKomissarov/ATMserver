package com.dkom.atm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientAuthentication {

    @Size(min = 16, max = 16, message = "must be 16 digits")
    private String cardNumber;

    @NotNull
    private String password;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
