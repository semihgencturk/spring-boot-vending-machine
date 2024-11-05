package com.tkpay.vendingmachine.controller;

import com.tkpay.vendingmachine.dto.WalletDto;
import com.tkpay.vendingmachine.model.WalletEntity;
import com.tkpay.vendingmachine.service.WalletService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WalletController implements BaseController {

    private final WalletService walletService;

    @GetMapping("/wallets")
    @JsonView(WalletDto.Basic.class)
    public List<WalletDto> getAllWalletDTOs(){
        return walletService.getAllWalletDTOs();
    }

    @GetMapping("/wallets/{id}")
    @JsonView(WalletDto.Detail.class)
    public WalletDto getWalletDTOById(@PathVariable("id") UUID id){
        return walletService.getWalletDTO(id);
    }

    @PostMapping("/wallets")
    public WalletEntity createWallet(@RequestBody WalletEntity wallet) {
        return walletService.saveWallet(wallet);
    }

    @PutMapping("/wallets/{id}")
    public WalletEntity updateWallet(@PathVariable("id") UUID id, @RequestBody WalletEntity wallet) {
        return walletService.updateWallet(id, wallet);
    }
}
