package com.example.cacvasbe.controllers;

import com.cloudinary.api.ApiResponse;
import com.example.cacvasbe.entities.WalletTransaction;
import com.example.cacvasbe.pojo.TransactionList;
import com.example.cacvasbe.pojo.TransactionResponse;
import com.example.cacvasbe.services.transaction.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "**")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("transac/history")
    public ResponseEntity<List<TransactionResponse>> getUserTransactions(
            @RequestParam("offset") int offset,
            @RequestParam("pageSize") int pageSize
    ){
        List<TransactionResponse> responses = transactionService.getUserTransactions(offset,pageSize);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<WalletTransaction>> getTransacHistory() {
       List<WalletTransaction> response = transactionService.allTransaction();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/total-amount")
    public ResponseEntity<BigDecimal> totalAmount() {
        BigDecimal response = transactionService.totalAmount();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/trans-length")
    public ResponseEntity<Integer> totalTrans() {
        int response = transactionService.totalTransaction();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
