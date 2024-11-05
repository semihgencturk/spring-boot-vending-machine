package com.tkpay.vendingmachine.repository;

import com.tkpay.vendingmachine.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {}
