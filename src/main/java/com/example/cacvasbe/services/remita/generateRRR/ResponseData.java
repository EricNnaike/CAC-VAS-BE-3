package com.example.cacvasbe.services.remita.generateRRR;

import java.io.Serializable;

public class ResponseData implements Serializable {
    private String amountDue;

    private String rrr;


    public String getAmountDue() {
        return amountDue;
    }


    public void setAmountDue(String amountDue) {
        this.amountDue = amountDue;
    }


    public String getRrr() {
        return rrr;
    }


    public void setRrr(String rrr) {
        this.rrr = rrr;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "amountDue='" + amountDue + '\'' +
                ", rrr='" + rrr + '\'' +
                '}';
    }
}
