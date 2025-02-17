package exchange.currencyexchange.services;

import exchange.currencyexchange.dto.CurrencyDTO;
import exchange.currencyexchange.dto.ExchangeAmountResultDto;
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
import java.math.RoundingMode;
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
        if (rate.compareTo(BigDecimal.ZERO) < 0) {
            throw new MessageException(ErrorMessage.EXCHANGE_RATE_SAVE_ERROR);
        }

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
                .baseCurrency(baseCurrency)
                .targetCurrency(targetCurrency)
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
    public ExchangeRateDTO getExchangeRatesByCurrencies(String codeDouble) throws MessageException {
        Optional<ExchangeRates> exchangeRateOpt = codeCheck(codeDouble);

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

    @Override
    public ExchangeAmountResultDto getExchangeAmount(String from, String to, BigDecimal amount) throws MessageException {
        BigDecimal rate = null;
        boolean isReversed = false;

        try {
            ExchangeRateDTO exchangeRate = getExchangeRatesByCurrencies(from + to);
            rate = exchangeRate.getRate();
        } catch (MessageException e) {
            try {
                ExchangeRateDTO exchangeRate = getExchangeRatesByCurrencies(to + from);
                rate = BigDecimal.ONE.divide(exchangeRate.getRate(), 6, RoundingMode.HALF_UP);
                isReversed = true;
            } catch (MessageException ex) {
                rate = getRateThroughUsd(from, to);
            }
        }

        if (rate == null) {
            throw new MessageException(ErrorMessage.EXCHANGE_RATE_NOT_FOUND);
        }

        BigDecimal convertedAmount = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);

        Currencies baseCurrency = currenciesRepository.findByCode(isReversed ? to : from);
        Currencies targetCurrency = currenciesRepository.findByCode(isReversed ? from : to);

        if (baseCurrency == null || targetCurrency == null) {
            throw new MessageException(ErrorMessage.CURRENCY_NOT_FOUND);
        }

        ExchangeAmountResultDto resultDto = new ExchangeAmountResultDto();
        resultDto.setBaseCurrency(new CurrencyDTO(baseCurrency));
        resultDto.setTargetCurrency(new CurrencyDTO(targetCurrency));
        resultDto.setRate(rate);
        resultDto.setAmount(amount);
        resultDto.setConvertedAmount(convertedAmount);

        return resultDto;
    }

    private BigDecimal getRateThroughUsd(String from, String to) throws MessageException {
        try {
            ExchangeRateDTO exchangeRateFromToUsd = getExchangeRatesByCurrencies(from + "USD");
            ExchangeRateDTO exchangeRateToUsdTo = getExchangeRatesByCurrencies(to + "USD");

            return exchangeRateFromToUsd.getRate().divide(exchangeRateToUsdTo.getRate(), 6, RoundingMode.HALF_UP);
        } catch (MessageException e) {
            return null;
        }
    }


    private ExchangeRateDTO convertToDTO(ExchangeRates model) {
        ExchangeRateDTO dto = new ExchangeRateDTO();
        dto.setId(model.getId());

        if (model.getBaseCurrency() != null) {
            dto.setBaseCurrency(convertCurrencyToDTO(model.getBaseCurrency()));
        } else {
            throw new IllegalArgumentException("Base currency is null");
        }

        if (model.getTargetCurrency() != null) {
            dto.setTargetCurrency(convertCurrencyToDTO(model.getTargetCurrency()));
        } else {
            throw new IllegalArgumentException("Target currency is null");
        }

        dto.setRate(model.getRate());

        return dto;
    }

    private CurrencyDTO convertCurrencyToDTO(Currencies currency) {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setId(currency.getId());
        currencyDTO.setCode(currency.getCode());
        return currencyDTO;
    }

}
