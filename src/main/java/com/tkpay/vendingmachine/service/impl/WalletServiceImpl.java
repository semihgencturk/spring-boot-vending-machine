package com.tkpay.vendingmachine.service.impl;

import com.tkpay.vendingmachine.dto.WalletDto;
import com.tkpay.vendingmachine.model.WalletEntity;
import com.tkpay.vendingmachine.repository.WalletRepository;
import com.tkpay.vendingmachine.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final ModelMapper modelMapper;
    private final WalletRepository walletRepository;

    @Override
    public List<WalletEntity> getAllWallets() {
        return walletRepository.findAll();
    }

    @Override
    public List<WalletDto> getAllWalletDTOs() {
        List<WalletEntity> wallets = getAllWallets();
        return wallets
                .stream()
                .map(wallet -> modelMapper.map(wallet, WalletDto.class))
                .toList();
    }

    @Override
    public WalletEntity getWallet(UUID id) {
        return walletRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Wallet with ID" + id + "Not Found!"));
    }

    @Override
    public WalletDto getWalletDTO(UUID id) {
        WalletEntity wallet = getWallet(id);
        return modelMapper.map(wallet, WalletDto.class);
    }

    @Override
    public WalletEntity saveWallet(WalletEntity wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public WalletEntity updateWallet(UUID id, WalletEntity wallet) {
        return walletRepository.findById(id)
                .map(myWallet -> {
                    myWallet.setBalance(wallet.getBalance());
                    myWallet.setActive(wallet.isActive());
                    return walletRepository.save(myWallet);
                })
                .orElseGet(() -> walletRepository.save(wallet));
    }

    @Override
    public void checkWalletForTransaction(WalletEntity wallet) {
        checkWalletId(wallet.getId());
        System.out.println(wallet.isActive());
//        checkWalletUsability(wallet.isUsability());
    }

    private void checkWalletId(UUID id) {
        getWallet(id);
    }

//    public void checkWalletUsability(boolean usability) {
//        System.out.println(usability);
//        if(!usability) {
//            throw new IllegalStateException("Wallet cannot be used since it is not usable!");
//        }
//    }
}
