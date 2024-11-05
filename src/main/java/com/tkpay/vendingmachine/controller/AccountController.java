package com.tkpay.vendingmachine.controller;

import com.tkpay.vendingmachine.dto.AccountDto;
import com.tkpay.vendingmachine.model.AccountEntity;
import com.tkpay.vendingmachine.service.AccountService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController implements BaseController {

    private final AccountService accountService;

    @GetMapping
    @JsonView(AccountDto.Basic.class)
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccountDtos();
    }

    @GetMapping("/{id}")
    @JsonView(AccountDto.Detail.class)
    public AccountDto getAccount(@PathVariable("id") UUID id) {
        return accountService.getAccountDto(id);
    }

    @PostMapping
    public AccountEntity createAccount(@RequestBody AccountEntity account) {
        return accountService.saveAccount(account);
    }

    @PutMapping("/{id}")
    public AccountEntity updateAccount(@PathVariable("id") UUID id, @RequestBody @Valid AccountEntity account) {
        return accountService.updateAccount(id, account);
    }

}