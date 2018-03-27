package com.dkom.atm.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PaymentCardRequest {
    @NotNull
    private String name;
    @Size(min = 16, max = 16)
    private String cardNumber;
    @NotNull
    private String birthday;
    @NotNull
    private String sex;
    @NotNull
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
