package com.tkpay.vendingmachine.repository;

import com.tkpay.vendingmachine.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {}
