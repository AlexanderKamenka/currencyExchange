package exchange.currencyexchange.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ExchangeAmountResultDto {

    private Integer id;
    private CurrencyDTO baseCurrency;
    private CurrencyDTO targetCurrency;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal convertedAmount;


}


