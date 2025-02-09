package exchange.currencyexchange.services;

import exchange.currencyexchange.dto.ExchangeRateDTO;
import exchange.currencyexchange.exceptions.ErrorMessage;
import exchange.currencyexchange.exceptions.MessageException;
import exchange.currencyexchange.models.Currencies;
import exchange.currencyexchange.models.ExchangeRates;
import exchange.currencyexchange.repository.CurrenciesRepository;
import exchange.currencyexchange.repository.ExchangeRatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeServicesImpl implements ExchangeServices {

    private final ExchangeRatesRepository exchangeRatesRepository;
    private final CurrenciesRepository currenciesRepository;

    @Override
    public ExchangeRateDTO addExchangeRate(String baseCurrencyCode, String targetCurrencyCode, BigDecimal rate) throws MessageException {
        Currencies baseCurrency = currenciesRepository.findByCode(baseCurrencyCode);
        Currencies targetCurrency = currenciesRepository.findByCode(targetCurrencyCode);

        if (baseCurrency == null || targetCurrency == null) {
            throw new MessageException(ErrorMessage.CURRENCY_NOT_FOUND);
        }

        boolean exists = exchangeRatesRepository.existsByBaseCurrencyIdAndTargetCurrencyId(baseCurrency.getId(), targetCurrency.getId());
        if (exists) {
            throw new MessageException(ErrorMessage.EXCHANGE_RATE_EXISTS);
        }

        ExchangeRates newExchangeRate = ExchangeRates.builder()
                .baseCurrencyId(baseCurrency.getId())
                .targetCurrencyId(targetCurrency.getId())
                .rate(rate)
                .build();

        ExchangeRates savedExchangeRate = exchangeRatesRepository.save(newExchangeRate);
        return convertToDTO(savedExchangeRate);
    }

    @Override
    public List<ExchangeRateDTO> getExchangeRates() {
        return exchangeRatesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExchangeRateDTO getExchangeRatesByCurrencies(String code) throws MessageException {
        Optional<ExchangeRates> exchangeRateOpt = codeCheck(code);

        return exchangeRateOpt.map(this::convertToDTO).orElseThrow(() ->
                new MessageException(ErrorMessage.EXCHANGE_RATE_NOT_FOUND));
    }

    private Optional<ExchangeRates> codeCheck(String code) throws MessageException {
        if (code == null || code.length() != 6) {
            throw new MessageException(ErrorMessage.INVALID_CURRENCY_CODE);
        }
        String baseCurrencyCode = code.substring(0, 3);
        String targetCurrencyCode = code.substring(3, 6);

        Currencies baseCurrency = currenciesRepository.findByCode(baseCurrencyCode);
        Currencies targetCurrency = currenciesRepository.findByCode(targetCurrencyCode);
        if (baseCurrency == null || targetCurrency == null) {
            throw new MessageException(ErrorMessage.CURRENCY_NOT_FOUND);
        }

        return exchangeRatesRepository
                .findByBaseCurrencyIdAndTargetCurrencyId(baseCurrency.getId(), targetCurrency.getId());
    }

    @Override
    public ExchangeRateDTO changeExchangeRate(String code, BigDecimal rate) throws MessageException {
        Optional<ExchangeRates> exchangeRateOpt = codeCheck(code);
        if (exchangeRateOpt.isPresent()) {
            ExchangeRates exchangeRate = exchangeRateOpt.get();
            exchangeRate.setRate(rate);
            exchangeRatesRepository.save(exchangeRate);

            return convertToDTO(exchangeRate);
        } else {
            throw new MessageException(ErrorMessage.EXCHANGE_RATE_NOT_FOUND);
        }
    }

    private ExchangeRateDTO convertToDTO(ExchangeRates model) {
        ExchangeRateDTO dto = new ExchangeRateDTO();
        dto.setId(model.getId());
        dto.setBaseCurrency(currenciesRepository.findById(model.getBaseCurrencyId()));
        dto.setTargetCurrency(currenciesRepository.findById(model.getTargetCurrencyId()));
        dto.setRate(model.getRate());
        return dto;
    }
}
