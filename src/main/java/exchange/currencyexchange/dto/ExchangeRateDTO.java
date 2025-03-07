package exchange.currencyexchange.dto;

import exchange.currencyexchange.models.Currencies;
import exchange.currencyexchange.models.ExchangeRates;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

@Data
public class ExchangeRateDTO {
    private Integer id;
    private CurrencyDTO baseCurrency;
    private CurrencyDTO targetCurrency;
    private BigDecimal rate;


}
