package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionsRepository extends JpaRepository<WalletTransaction, Long> {

}
