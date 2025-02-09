package exchange.currencyexchange.services;


import exchange.currencyexchange.dto.CurrencyDTO;
import exchange.currencyexchange.exceptions.MessageException;

import java.util.List;

public interface CurrencyServices {
    CurrencyDTO saveCurrency(String code, String name, String sign) throws MessageException;
    List<CurrencyDTO> getCurrencies();

    CurrencyDTO getCurrencyByCode(String code) throws MessageException;
}
