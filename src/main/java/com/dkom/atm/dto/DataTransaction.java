package com.dkom.atm.dto;

public class DataTransaction {
    private String numberSender;
    private String numberDestination;
    private float tranzactionAmount;
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
