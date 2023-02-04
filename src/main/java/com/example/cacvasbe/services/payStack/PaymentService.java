package com.example.cacvasbe.services.payStack;

import com.example.cacvasbe.dto.PayStackTransactionRequest;
import com.example.cacvasbe.pojo.PayStackTransactionResponse;


public interface PaymentService {
     PayStackTransactionResponse initTransaction(PayStackTransactionRequest request) throws Exception;
}

