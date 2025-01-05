package exchange.currencyexchange.services;

import exchange.currencyexchange.dto.CurrencyDTO;
import exchange.currencyexchange.exceptions.CurrencyAddException;
import exchange.currencyexchange.model.Currencies;
import exchange.currencyexchange.repository.CurrenciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyServicesImpl implements CurrencyServices {

    private final CurrenciesRepository currenciesRepository;

    @Autowired
    public CurrencyServicesImpl(CurrenciesRepository currenciesRepository) {
        this.currenciesRepository = currenciesRepository;
    }

    @Override
    public CurrencyDTO saveCurrency(String code, String name, String sign) throws CurrencyAddException {
        if (currenciesRepository.existsByCode(code)) {
            throw new CurrencyAddException("The currency with this code already exists.");
        }

        Currencies model = new Currencies();
        model.setCode(code);
        model.setName(name);
        model.setSign(sign);

        try {
            Currencies savedCurrency = currenciesRepository.save(model);
            return new CurrencyDTO(
                    savedCurrency.getId(),
                    savedCurrency.getName(),
                    savedCurrency.getCode(),
                    savedCurrency.getSign()
            );

        } catch (DataIntegrityViolationException e) {
            throw new CurrencyAddException("Currency saving error: violation of uniqueness.");
        } catch (Exception e) {
            throw new CurrencyAddException("Unknown error when saving currency", e);
        }
    }

    @Override
    public List<CurrencyDTO> getCurrencies() {
        Iterable<Currencies> currencies = currenciesRepository.findAll();
        List<CurrencyDTO> currencyDTOs = new ArrayList<>();
        for (Currencies currency : currencies) {
            currencyDTOs.add(new CurrencyDTO(currency.getId(), currency.getName(), currency.getCode(), currency.getSign()));
        }
        return currencyDTOs;
    }
}
