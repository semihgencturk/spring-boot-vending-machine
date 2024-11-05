package com.tkpay.vendingmachine.controller;

import com.tkpay.vendingmachine.dto.TransactionDto;
import com.tkpay.vendingmachine.model.TransactionEntity;
import com.tkpay.vendingmachine.service.TransactionService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController implements BaseController {

    private final TransactionService transactionService;

    @GetMapping
    @JsonView(TransactionDto.Basic.class)
    public List<TransactionDto> getAllTransactionDTOs() {
        return transactionService.getAllTransactionDTOs();
    }

    @GetMapping("/{id}")
    @JsonView(TransactionDto.Detail.class)
    public TransactionDto getTransactionDTO(@PathVariable("id") UUID id) {
        return transactionService.getTransactionDTO(id);
    }

    @PostMapping
    public TransactionEntity createTransaction(@RequestBody TransactionEntity transaction) {
        return transactionService.saveTransaction(transaction);
    }
}
