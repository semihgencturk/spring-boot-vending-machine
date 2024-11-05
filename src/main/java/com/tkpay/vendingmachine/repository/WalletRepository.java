package com.tkpay.vendingmachine.repository;

import com.tkpay.vendingmachine.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {
}
