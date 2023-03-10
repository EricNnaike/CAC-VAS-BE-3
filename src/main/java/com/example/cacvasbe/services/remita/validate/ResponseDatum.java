package com.example.cacvasbe.services.remita.validate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ResponseDatum implements Serializable {
    private List<CustomField> customFields = null;

    private String billId;

    private BigDecimal amount;

    private String payerPhone;

    private String currency;

    private String payerName;

    private String payerEmail;

    private BigDecimal amountDue;

    private String status;


    public List<CustomField> getCustomFields() {
        return customFields;
    }


    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }


    public String getBillId() {
        return billId;
    }


    public void setBillId(String billId) {
        this.billId = billId;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public String getPayerPhone() {
        return payerPhone;
    }


    public void setPayerPhone(String payerPhone) {
        this.payerPhone = payerPhone;
    }


    public String getCurrency() {
        return currency;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getPayerName() {
        return payerName;
    }


    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }


    public String getPayerEmail() {
        return payerEmail;
    }


    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }


    public BigDecimal getAmountDue() {
        return amountDue;
    }


    public void setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ResponseDatum{" +
                "customFields=" + customFields +
                ", billId='" + billId + '\'' +
                ", amount=" + amount +
                ", payerPhone='" + payerPhone + '\'' +
                ", currency='" + currency + '\'' +
                ", payerName='" + payerName + '\'' +
                ", payerEmail='" + payerEmail + '\'' +
                ", amountDue=" + amountDue +
                ", status='" + status + '\'' +
                '}';
    }
}
