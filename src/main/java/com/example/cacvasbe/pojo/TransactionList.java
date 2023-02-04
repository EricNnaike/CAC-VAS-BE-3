package com.example.cacvasbe.pojo;

import com.example.cacvasbe.entities.WalletTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionList {
    private String message;
    private boolean status;
    List<WalletTransaction> walletTransactionList;
}
