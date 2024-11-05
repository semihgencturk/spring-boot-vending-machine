package com.tkpay.vendingmachine.service.impl;

import com.tkpay.vendingmachine.dto.CurrencyDto;
import com.tkpay.vendingmachine.model.CurrencyEntity;
import com.tkpay.vendingmachine.repository.CurrencyRepository;
import com.tkpay.vendingmachine.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.tkpay.vendingmachine.config.CurrencyConfig.validCurrencyCodes;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final ModelMapper modelMapper;
    private final CurrencyRepository currencyRepository;

    @Override
    public List<CurrencyEntity> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public List<CurrencyDto> getAllCurrencyDTOs() {
        List<CurrencyEntity> currencies = getAllCurrencies();
        return currencies
                .stream()
                .map(currency -> modelMapper.map(currency, CurrencyDto.class))
                .toList();
    }

    @Override
    public CurrencyEntity getCurrency(Long id) {
        return currencyRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Currency with ID" + id + "not found!"));
    }

    @Override
    public CurrencyDto getCurrencyDTO(Long id) {
        CurrencyEntity currency = getCurrency(id);
        return modelMapper.map(currency, CurrencyDto.class);
    }

    @Override
    public CurrencyEntity saveCurrency(CurrencyEntity currency) {
        validateCurrencyBeforeSave(currency);
        return currencyRepository.save(currency);
    }

    @Override
    public CurrencyEntity updateCurrency(Long id, CurrencyEntity currency) {
        return currencyRepository.findById(id)
                .map(myCurrency -> {
                    myCurrency.setName(currency.getName());
                    myCurrency.setStatus(currency.isStatus());
                    return currencyRepository.save(myCurrency);
                })
                .orElseGet(() -> currencyRepository.save(currency));
    }

    private void validateCurrencyBeforeSave(CurrencyEntity currency) {
        boolean isNumericCodeValid = false;
        boolean isAlphabeticCodeValid = false;

        for(Map<String, String> validCurrencyCode: validCurrencyCodes) {
            if(validCurrencyCode.get("numericCode").equals(currency.getNumericCode())) {
                isNumericCodeValid = true;
            }
            if(validCurrencyCode.get("alphabeticCode").equals(currency.getAlphabeticCode())){
                isAlphabeticCodeValid = true;
            }
        }

        if(!isNumericCodeValid) {
            throw new IllegalArgumentException("Numeric Code isn't valid!");
        }
        if(!isAlphabeticCodeValid) {
            throw new IllegalArgumentException("Alphabetic Code isn't valid!");
        }
    }
}
