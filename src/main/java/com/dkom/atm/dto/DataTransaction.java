package com.dkom.atm.dto;

import javax.validation.constraints.*;

public class DataTransaction {

    @Size(min = 16, max = 16)
    private String numberSender;

    @Size(min = 16, max = 16)
    private String numberDestination;

    @NotNull
    @DecimalMin(value = "0")
    private float tranzactionAmount;

    @NotNull
    private String password;

    public String getNumberSender() {
        return numberSender;
    }

    public void setNumberSender(String numberSender) {
        this.numberSender = numberSender;
    }

    public String getNumberDestination() {
        return numberDestination;
    }

    public void setNumberDestination(String numberDestination) {
        this.numberDestination = numberDestination;
    }

    public float getTranzactionAmount() {
        return tranzactionAmount;
    }

    public void setTranzactionAmount(float tranzactionAmount) {
        this.tranzactionAmount = tranzactionAmount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
