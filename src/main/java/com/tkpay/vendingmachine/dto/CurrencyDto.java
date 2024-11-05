package com.tkpay.vendingmachine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {
    public interface Basic {}
    public interface Detail extends Basic {}

    @JsonView({Basic.class})
    private Long id;

    @JsonView({Detail.class})
    private String numericCode;

    @JsonView({Basic.class, ProductDto.Basic.class, WalletDto.Basic.class})
    @JsonProperty(value = "currency")
    private String alphabeticCode;

    @JsonView({Basic.class, WalletDto.Basic.class})
    private String name;

    @JsonView({Detail.class})
    private boolean status;
}