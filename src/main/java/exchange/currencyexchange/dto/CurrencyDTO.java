package exchange.currencyexchange.dto;

import exchange.currencyexchange.models.Currencies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO {
    private Integer id;
    private String name;
    private String code;
    private String sign;

    public CurrencyDTO(Currencies baseCurrency) {
        this.id = baseCurrency.getId();
        this.name = baseCurrency.getName();
        this.code = baseCurrency.getCode();
        this.sign = baseCurrency.getSign();

    }
}

