package exchange.currencyexchange.dto;

import exchange.currencyexchange.models.Currencies;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

@Data
public class ExchangeRateDTO {
    private Integer id;
    private Currencies baseCurrency;
    private Currencies targetCurrency;
    private BigDecimal rate;

    public void setBaseCurrency(Optional<Currencies> byId) {
        byId.ifPresent(currencies -> baseCurrency = currencies);
    }
    public void setTargetCurrency(Optional<Currencies> byId) {
        byId.ifPresent(currencies -> targetCurrency = currencies);
    }
}
