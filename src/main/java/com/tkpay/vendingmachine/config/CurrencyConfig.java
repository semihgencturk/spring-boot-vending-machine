package com.tkpay.vendingmachine.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;

@Configuration
public class CurrencyConfig {

    private final ObjectMapper objectMapper;

    @Getter
    public static Set<Map<String, String>> validCurrencyCodes;

    public CurrencyConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Value("classpath:static/valid_currency_codes.json")
    private Resource validCurrencyCodesJson;

    @PostConstruct
    private void loadValidCurrencies() throws IOException {
        List<Map<String, String>> validCurrencyCodesList = objectMapper.readValue(
                validCurrencyCodesJson.getInputStream(),
                new TypeReference<>() {
                }
        );
        validCurrencyCodes = new HashSet<>(validCurrencyCodesList);
    }
}
