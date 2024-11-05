package com.tkpay.vendingmachine.repository;

import com.tkpay.vendingmachine.model.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {
}
