package com.tkpay.vendingmachine.service.impl;

import com.tkpay.vendingmachine.dto.AccountDto;
import com.tkpay.vendingmachine.enums.AccountStatus;
import com.tkpay.vendingmachine.enums.AccountType;
import com.tkpay.vendingmachine.model.AccountEntity;
import com.tkpay.vendingmachine.repository.AccountRepository;
import com.tkpay.vendingmachine.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    @Override
    public List<AccountEntity> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDto> getAllAccountDtos() {
        List<AccountEntity> accounts = accountRepository.findAll();
        return accounts
                .stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .toList();
    }

    @Override
    public AccountEntity getAccount(UUID id) {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Account with ID" + id + "not found!"));

    }

    @Override
    public AccountDto getAccountDto(UUID id) {
        AccountEntity account = accountRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Account with ID" + id + "not found!"));
        return modelMapper.map(account, AccountDto.class);
    }

    @Override
    @Transactional
    public AccountEntity saveAccount(AccountEntity account) {
        prepareAccountBeforeSave(account);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public AccountEntity updateAccount(UUID id, AccountEntity account) {
        return accountRepository.findById(id)
                .map(myAccount -> {
                    myAccount.setPassword(account.getPassword());
                    myAccount.setEmail(account.getEmail());
                    myAccount.setPhoneNumber(account.getPhoneNumber());
                    myAccount.setName(account.getName());
                    myAccount.setSurname(account.getSurname());
                    myAccount.setBirthDate(account.getBirthDate());
                    myAccount.setType(account.getType());
                    myAccount.setStatus(account.getStatus());
                    myAccount.setWallets(account.getWallets());
                    return accountRepository.save(myAccount);
                })
                .orElseGet(() -> accountRepository.save(account));
    }

    private void assignCustomerNo(AccountEntity account) {
        //Generate a customer No
        Random randomNumber = new Random();
        String customerNo = String.format("%010d", randomNumber.nextLong(1000000000));

        //Check whether it is already exist in db
        List<AccountEntity> accounts = getAllAccounts();
        for(AccountEntity myAccount : accounts) {
            if(customerNo.equals(myAccount.getCustomerNo())){
                assignCustomerNo(account);
            }
        }

        //Assign it
        account.setCustomerNo(customerNo);
    }

    private void assignAccountType(AccountEntity account) {
        if(account.getType() == null) {
            account.setType(AccountType.BASIC);
        }
    }

    private void assignAccountStatus(AccountEntity account) {
        if(account.getStatus() == null) {
            account.setStatus(AccountStatus.PASSIVE);
        }
    }

    private void prepareAccountBeforeSave(AccountEntity account) {
        assignCustomerNo(account);
        assignAccountType(account);
        assignAccountStatus(account);
    }

    private void checkAccountRestricted(AccountStatus status) {
        if(status == AccountStatus.RESTRICTED) {
            throw new IllegalStateException("Wallet's Account is restricted");
        }
        if(status == AccountStatus.PASSIVE) {
            throw new IllegalStateException("Wallet's Account is passive");
        }
        if(status == AccountStatus.CLOSED) {
            throw new IllegalStateException("Wallet's Account is closed");
        }
    }

    private void checkAccountType(AccountType accountType) {
        if(accountType == AccountType.CHILD) {
            throw new IllegalStateException("Child Wallet can");
        }
    }

    @Override
    public void checkAccountForTransaction(AccountEntity account) {
        checkAccountRestricted(account.getStatus());
        checkAccountType(account.getType());
    }

}