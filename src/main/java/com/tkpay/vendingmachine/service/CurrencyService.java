package com.tkpay.vendingmachine.service;

import com.tkpay.vendingmachine.dto.CurrencyDto;
import com.tkpay.vendingmachine.model.CurrencyEntity;

import java.util.List;

public interface CurrencyService {
    List<CurrencyEntity> getAllCurrencies();
    List<CurrencyDto> getAllCurrencyDTOs();
    CurrencyEntity getCurrency(Long id);
    CurrencyDto getCurrencyDTO(Long id);
    CurrencyEntity saveCurrency(CurrencyEntity currency);
    CurrencyEntity updateCurrency(Long id, CurrencyEntity currency);
}
