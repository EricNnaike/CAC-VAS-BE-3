package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.entities.WalletTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepository extends JpaRepository<WalletTransaction, Long> {
    Page<WalletTransaction>  findWalletTransactionByUsernameOrderByCreatedTime(Pageable pageable, PortalUsers user);

    @Query(value = "SELECT * FROM wallet_transaction WHERE username_id = ?1", nativeQuery = true)
    List<WalletTransaction> transactionList(Long id);

//    List<WalletTransaction> findAllByUserId(Long id);

}
