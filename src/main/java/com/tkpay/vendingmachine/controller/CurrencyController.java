package com.tkpay.vendingmachine.controller;

import com.tkpay.vendingmachine.dto.CurrencyDto;
import com.tkpay.vendingmachine.model.CurrencyEntity;
import com.tkpay.vendingmachine.service.CurrencyService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyController implements BaseController {

    private final CurrencyService currencyService;

    @GetMapping
    @JsonView(CurrencyDto.Basic.class)
    public List<CurrencyDto> getAllCurrencies(){
        return currencyService.getAllCurrencyDTOs();
    }

    @GetMapping("/{id}")
    @JsonView(CurrencyDto.Detail.class)
    public CurrencyDto getCurrency(@PathVariable("id") Long id) {
        return currencyService.getCurrencyDTO(id);
    }

    @PostMapping
    public CurrencyEntity createCurrency(@RequestBody CurrencyEntity currency) {
        return currencyService.saveCurrency(currency);
    }

    @PutMapping("/{id}")
    public CurrencyEntity updateCurrency(@PathVariable("id") Long id, @RequestBody @Valid CurrencyEntity currency) {
        return currencyService.updateCurrency(id, currency);
    }
}