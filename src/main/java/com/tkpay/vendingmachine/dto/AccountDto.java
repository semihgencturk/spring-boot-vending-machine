package com.tkpay.vendingmachine.dto;

import com.tkpay.vendingmachine.enums.AccountStatus;
import com.tkpay.vendingmachine.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonView;
import com.tkpay.vendingmachine.model.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    public interface Basic {}
    public interface Detail extends Basic {}

    @JsonView({Basic.class})
    private UUID id;

    @JsonView({Basic.class, WalletDto.Basic.class})
    private String customerNo;

    @JsonView({Detail.class})
    private String email;

    @JsonView({Detail.class})
    private String phoneNumber;

    @JsonView({Detail.class})
    private String name;

    @JsonView({Detail.class})
    private String surname;

    @JsonView({Detail.class})
    private LocalDate birthDate;

    @JsonView({Basic.class})
    private AccountType type;

    @JsonView({Basic.class})
    private AccountStatus status;

    @JsonView({Basic.class})
    private Set<WalletDto> wallets;

    @JsonView({Detail.class})
    private Instant createdDate;

    @JsonView({Detail.class})
    private Instant lastUpdatedDate;


    public static AccountDto basicFromEntity(AccountEntity account){
        return AccountDto.builder()
                .status(account.getStatus())
                .build();
    }
}
