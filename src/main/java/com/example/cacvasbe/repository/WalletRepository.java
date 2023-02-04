package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.LicensedPartners;
import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findWalletsByUsername(PortalUsers user);
    Optional<Wallet> findWalletsByUsername(LicensedPartners user);
}
