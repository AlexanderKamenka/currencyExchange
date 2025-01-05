package exchange.currencyexchange.services;


import exchange.currencyexchange.dto.CurrencyDTO;
import exchange.currencyexchange.exceptions.CurrencyAddException;

import java.util.List;

public interface CurrencyServices {
    CurrencyDTO saveCurrency(String code, String name, String sign) throws CurrencyAddException;
    List<CurrencyDTO> getCurrencies();
}
