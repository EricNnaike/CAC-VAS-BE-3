package com.example.cacvasbe.services.remita.utils;

import ng.com.systemspecs.remitabillinggateway.configuration.Credentials;

public interface Connection {
    /**
     * This method sends HTTP GET request to a given URL
     *
     * @param url holds the URL to Remita Billing Gateway API we're trying to call
     * @param credentials
//     * @see Credentials
     */
    String sendGET(String url, Credentials credentials) throws Exception;

    /**
     * This method sends HTTP POST request to a given URL
     *
     * @param url holds the URL to Remita Billing Gateway API we're trying to call
     * @param payloadObject Object that holds the request payload
     * @param credentials
//     * @see Credentials
     */
    String sendPOST(String url, Credentials credentials, Object payloadObject) throws Exception;

    /**
     * This method sends HTTP POST request to a given URL with TXN_HASH in the header preset
     *
     * @param url holds the URL to Remita Billing Gateway API we're trying to call
     * @param payloadObject Object that holds the request payload
     * @param credentials
     * @see Credentials
     */
    String sendPOSTWithHash(String url, Credentials credentials, Object payloadObject, String transHash) throws Exception;
}
