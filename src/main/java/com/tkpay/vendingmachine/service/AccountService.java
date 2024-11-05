package com.tkpay.vendingmachine.service;

import com.tkpay.vendingmachine.dto.AccountDto;
import com.tkpay.vendingmachine.model.AccountEntity;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountDto getAccountDto(UUID id);
    AccountEntity getAccount(UUID id);
    List<AccountDto> getAllAccountDtos();
    List<AccountEntity> getAllAccounts();
    AccountEntity saveAccount(AccountEntity account);
    void checkAccountForTransaction(AccountEntity account);
    AccountEntity updateAccount(UUID id, AccountEntity account);

}
