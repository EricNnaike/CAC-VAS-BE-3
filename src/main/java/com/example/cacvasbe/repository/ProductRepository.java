package com.example.cacvasbe.repository;

import com.example.cacvasbe.entities.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductList, Long> {
    Optional<ProductList> findProductListById(Long id);

}
