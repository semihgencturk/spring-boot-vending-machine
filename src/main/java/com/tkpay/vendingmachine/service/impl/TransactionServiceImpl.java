package com.tkpay.vendingmachine.service.impl;

import com.tkpay.vendingmachine.dto.TransactionDto;
import com.tkpay.vendingmachine.model.AccountEntity;
import com.tkpay.vendingmachine.model.ProductEntity;
import com.tkpay.vendingmachine.model.TransactionEntity;
import com.tkpay.vendingmachine.model.WalletEntity;
import com.tkpay.vendingmachine.repository.TransactionRepository;
import com.tkpay.vendingmachine.service.AccountService;
import com.tkpay.vendingmachine.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final ModelMapper modelMapper;
    private final AccountService accountService;
    private final WalletServiceImpl walletService;
    private final ProductServiceImpl productService;
    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionEntity> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<TransactionDto> getAllTransactionDTOs() {
        List<TransactionEntity> transactions = getAllTransactions();
        return transactions
                .stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class))
                .toList();
    }

    @Override
    public TransactionEntity getTransaction(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Transaction with ID" + id + "not found!"));
    }

    @Override
    public TransactionDto getTransactionDTO(UUID id) {
        TransactionEntity transaction =  getTransaction(id);
        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Override
    @Transactional
    public TransactionEntity saveTransaction(TransactionEntity transaction) {
        executeTransaction(transaction);
        return transactionRepository.save(transaction);
    }

    private void executeTransaction(TransactionEntity transaction) {
        // Get Transaction Dependencies
        List<ProductEntity> products = getProducts(transaction.getProducts());
        WalletEntity wallet = walletService.getWallet(transaction.getWallet().getId());
        AccountEntity account = accountService.getAccount(wallet.getAccount().getId());

        // Check Transaction Dependencies
        productService.checkProductsForTransaction(products);
        walletService.checkWalletForTransaction(wallet);
        accountService.checkAccountForTransaction(account);

        // Calculate Purchase Amount And Validate Balance
        BigDecimal purchaseAmount = calculatePurchaseAmount(products);
        BigDecimal balance = wallet.getBalance();
        compareAmountAndBalance(purchaseAmount, balance);

        // Execute Updates In Db
        reduceProductStock(products);
        reduceWalletBalance(wallet, purchaseAmount);
    }

    private List<ProductEntity> getProducts(List<ProductEntity> transactionProducts) {
        return transactionProducts.stream()
                .map(product -> productService.getProduct(product.getId()))
                .toList();
    }

    private BigDecimal calculatePurchaseAmount(List<ProductEntity> products) {
        BigDecimal purchaseAmount = BigDecimal.ZERO;

        for(ProductEntity product : products) {
            purchaseAmount = purchaseAmount.add(product.getAmount());
        }

        return purchaseAmount;
    }

    private void compareAmountAndBalance(BigDecimal purchaseAmount, BigDecimal balance) {
        if(balance.compareTo(purchaseAmount) < 0) {
            throw new IllegalStateException("Wallet balance isn't enough for transaction!");
        }
    }

    private void reduceProductStock(List<ProductEntity> products) {
        products.forEach(product -> {
            product.setCount(product.getCount() - 1);
            productService.updateProduct(product.getId(), product);
        });
    }

    private void reduceWalletBalance(WalletEntity wallet, BigDecimal purchaseAmount) {
        BigDecimal newBalance = wallet.getBalance().subtract(purchaseAmount);
        wallet.setBalance(newBalance);
        walletService.updateWallet(wallet.getId(), wallet);
    }
}