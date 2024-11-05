package com.tkpay.vendingmachine.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tkpay.vendingmachine.enums.Currencies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {
    public interface Basic {}
    public interface Detail extends Basic {}

    @JsonView({Basic.class, AccountDto.Basic.class, TransactionDto.Basic.class})
    private UUID id;

    @JsonView({Basic.class})
    private BigDecimal balance;

    @JsonView({Basic.class})
    private Currencies currency;

    @JsonView({Detail.class})
    private AccountDto account;

    @JsonView({Basic.class})
    private boolean active;

    @JsonView({Detail.class})
    private Instant createdDate;

    @JsonView({Detail.class})
    private Instant lastUpdatedDate;
}
