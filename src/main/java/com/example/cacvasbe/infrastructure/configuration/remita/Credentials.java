package com.example.cacvasbe.infrastructure.configuration.remita;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ng.com.systemspecs.remitabillinggateway.util.EnvironmentType;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

public class Credentials {
    /**
     * This field is a mandatory field for all method calls.
     * Located at the Billing page of your Remita profile on www.remita.net.
     *
     */
    private String publicKey = "key:UzAwMDA1MzUwNzh8MTEwMDUxMDg4NTQ2fDc5MDAzMGQzZWIzM2NiNzAzNzEyYmFmYzRkMzZiZWE2NTcyMDUwNWJlOWM1MTc3NzI4NjM3NTBjNjQxZjY1MTA3MDhlOWVlYTkyYjkxMDkyZWEyNDU2ZDY1OTA5MzJlZmU0MTE5ZDg1YjMxZDUzODUzNWIzOTA1NjE3YjYzNTI5";

    /**
     * This field is only mandatory for billNotification method calls.
     * Located at the Billing page of your Remita profile on www.remita.net.
     *
     */
    private String secretKey = "ecad9e293097de8830ebc7bdad8e7e69082fd999f534383f7b7ea5e5d3f231a7e88668211e2b4d4b9e9cc51e368145324dd2b5d86a7eaa49ffd6ef920e0e880d";

    /**
     * This field is an Optional field and will automatically be
     * set to 200000 milliseconds if not supplied.
     *
     */
    private int readTimeOut;

    /**
     * This field is an Optional field and will automatically be
     * set to 200000 milliseconds if not supplied.
     *
     */
    private int connectionTimeOut;

    /**
     * This field is only mandatory for billNotification method calls.
     *
     *
     */
    private String transactionId;

    /**
     * This field is an Optional field and can only take enum type EnvironmentType.LIVE
     * or EnvironmentType.DEMO. It will automatically be set to "EnvironmentType.DEMO" if not supplied.
     *
     */
    private EnvironmentType environment;


    public int getReadTimeOut() {
        if(this.readTimeOut == 0 || StringUtils.isEmpty(this.readTimeOut)) {
            this.readTimeOut = 200000;
        }
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getConnectionTimeOut() {
        if(this.connectionTimeOut == 0 || StringUtils.isEmpty(this.connectionTimeOut)) {
            this.connectionTimeOut = 200000;
        }
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public EnvironmentType getEnvironment() {
        if(this.environment == null || StringUtils.isEmpty(this.environment)) {
            this.environment = EnvironmentType.DEMO;
        }
        return environment;
    }

    public void setEnvironment(EnvironmentType environment) {
        this.environment = environment;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "publicKey='" + publicKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", readTimeOut=" + readTimeOut +
                ", connectionTimeOut=" + connectionTimeOut +
                ", transactionId='" + transactionId + '\'' +
                ", environment='" + environment + '\'' +
                '}';
    }
}
