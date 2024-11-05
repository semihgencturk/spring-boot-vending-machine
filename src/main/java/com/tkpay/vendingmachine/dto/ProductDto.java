package com.tkpay.vendingmachine.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tkpay.vendingmachine.enums.Currencies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    public interface Basic {}
    public interface Detail extends Basic {}

    @JsonView({Basic.class, TransactionDto.Basic.class})
    private Long id;

    @JsonView({Basic.class, TransactionDto.Basic.class})
    private String name;

    @JsonView({Basic.class})
    private BigDecimal amount;

    @JsonView({Basic.class})
    private Currencies currency;

    @JsonView({Basic.class})
    private int count;
}
