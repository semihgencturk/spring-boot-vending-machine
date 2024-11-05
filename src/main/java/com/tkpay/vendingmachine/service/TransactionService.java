package com.tkpay.vendingmachine.service;

import com.tkpay.vendingmachine.dto.TransactionDto;
import com.tkpay.vendingmachine.model.TransactionEntity;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    List<TransactionEntity> getAllTransactions();
    List<TransactionDto> getAllTransactionDTOs();
    TransactionEntity getTransaction(UUID id);
    TransactionDto getTransactionDTO(UUID id);
    TransactionEntity saveTransaction(TransactionEntity transaction);
}
