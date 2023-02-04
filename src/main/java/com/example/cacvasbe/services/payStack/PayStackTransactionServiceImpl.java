package com.example.cacvasbe.services.payStack;

import com.example.cacvasbe.dto.PayStackTransactionRequest;
import com.example.cacvasbe.pojo.PayStackTransactionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
@Transactional
public class PayStackTransactionServiceImpl implements PaymentService {

    /**
     * PayStack secret key
     */
    @Value("sk_test_00a46d45ddc6f130000e02c63def4fd09bdc588d")
    private String payStackSecretKey;
    private final PayStackVerification payStackVerification;
    public PayStackTransactionResponse initTransaction(PayStackTransactionRequest request) throws Exception {

        System.out.println(request);
        PayStackTransactionResponse PayStackTransactionResponse;
        try {
            // Converts object to json
            Gson gson = new Gson();
            // add payStack charges to the amount
            StringEntity postingString = new StringEntity(gson.toJson(request));

            //Consuming paystack api using web client
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("https://api.paystack.co/transaction/initialize");
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer "+payStackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);
            System.out.println(".............................");
            System.out.println(".............................");
            System.out.println(response);

//            if (request.getAmount().intValue() > 50000) {
//                throw new Exception("Amount must be #50,000");
//            }

            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

            } else {
                throw new AuthenticationException("Error Occurred while initializing transaction");
            }

            ObjectMapper mapper = new ObjectMapper();

            PayStackTransactionResponse = mapper.readValue(result.toString(), PayStackTransactionResponse.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failure initializing payStack transaction");
        }
//        PayStackVerification payStackVerification1 = payStackVerification.verifyTransaction(PayStackTransactionResponse.getData().getReference());
//        if(payStackVerification1 == null){
//            throw new Exception("Paystack verification failed");
//        }


        return PayStackTransactionResponse;
    }
}
