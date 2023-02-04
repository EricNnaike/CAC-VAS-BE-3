package com.example.cacvasbe.services.transaction;

import com.example.cacvasbe.entities.WalletTransaction;
import com.example.cacvasbe.pojo.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    List<TransactionResponse> getUserTransactions(int offset, int pageSize);
    List<WalletTransaction> allTransaction();
    BigDecimal totalAmount();
    Integer totalTransaction();

}
