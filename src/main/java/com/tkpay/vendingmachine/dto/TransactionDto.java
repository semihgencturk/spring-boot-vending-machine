package com.tkpay.vendingmachine.dto;

import com.tkpay.vendingmachine.enums.Currencies;
import com.tkpay.vendingmachine.enums.TransactionStatus;
import com.tkpay.vendingmachine.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    public interface Basic {}
    public interface Detail extends Basic {}

    @JsonView({Basic.class})
    private UUID id;

    @JsonView({Basic.class})
    private BigDecimal amount;

    @JsonView({Basic.class})
    private Currencies currency;

    @JsonView({Basic.class})
    private TransactionType type;

    @JsonView({Basic.class})
    private TransactionStatus status;

    @JsonView({Basic.class})
    private Instant createdDate;

    @JsonView({Basic.class})
    private Instant lastUpdatedDate;

    @JsonView({Basic.class})
    private WalletDto wallet;

    @JsonView({Basic.class})
    private List<ProductDto> product;
}