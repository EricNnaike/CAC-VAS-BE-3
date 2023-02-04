package com.example.cacvasbe.controllers;

import com.example.cacvasbe.dto.PayStackTransactionRequest;
import com.example.cacvasbe.dto.wallet.FundWalletRequest;
import com.example.cacvasbe.pojo.PayStackTransactionResponse;
import com.example.cacvasbe.pojo.TransactionResponse;
import com.example.cacvasbe.services.payStack.PayStackVerification;
import com.example.cacvasbe.services.payStack.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200/**")
public class PaystackController {

    private final PaymentService paymentService;
    private final PayStackVerification payStackVerification;

    @PostMapping("/pay")
    ResponseEntity<PayStackTransactionResponse> payment(@RequestBody PayStackTransactionRequest request) throws Exception {
        PayStackTransactionResponse response = paymentService.initTransaction(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping("/verify")
//    ResponseEntity<PayStackVerification> verifyPayment(@RequestParam("reference") String reference) throws Exception {
//        PayStackVerification response = payStackVerification.verifyTransaction(reference);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/verify")
    ResponseEntity<PayStackVerification> verifyPayment( @RequestParam("trxref") String txref, @RequestParam("reference") String reference) throws Exception {
        PayStackVerification response = payStackVerification.verifyTransaction(reference);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
