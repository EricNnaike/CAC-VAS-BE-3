package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.LicensedPartners;
import com.example.cacvasbe.entities.PurchasedProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchasedProductRepository extends JpaRepository<PurchasedProducts, Long> {
    List<PurchasedProducts> findAllByLicensedPartner(LicensedPartners licensedPartners);
    Optional<PurchasedProducts> deleteByProductsId(Long productId);
    Optional<PurchasedProducts> findPurchasedProductsById(Long id);

}
