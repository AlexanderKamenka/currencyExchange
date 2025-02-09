package exchange.currencyexchange.services;

import exchange.currencyexchange.dto.ExchangeRateDTO;
import exchange.currencyexchange.exceptions.MessageException;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeServices {
    ExchangeRateDTO addExchangeRate(String baseCurrencyCode, String targetCurrencyCode,  BigDecimal rate) throws MessageException;

    List<ExchangeRateDTO> getExchangeRates();

    ExchangeRateDTO getExchangeRatesByCurrencies(String code) throws MessageException;

    ExchangeRateDTO changeExchangeRate(String code, BigDecimal rate) throws MessageException;
}
