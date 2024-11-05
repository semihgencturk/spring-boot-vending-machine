package com.tkpay.vendingmachine.service;

import com.tkpay.vendingmachine.dto.WalletDto;
import com.tkpay.vendingmachine.model.WalletEntity;

import java.util.List;
import java.util.UUID;

public interface WalletService {
    List<WalletEntity> getAllWallets();
    List<WalletDto> getAllWalletDTOs();
    WalletEntity getWallet(UUID id);
    WalletDto getWalletDTO(UUID id);
    WalletEntity saveWallet(WalletEntity wallet);
    WalletEntity updateWallet(UUID id, WalletEntity wallet);
    void checkWalletForTransaction(WalletEntity wallet);
}
