package exchange.currencyexchange.services;

import exchange.currencyexchange.dto.CurrencyDTO;
import exchange.currencyexchange.exceptions.ErrorMessage;
import exchange.currencyexchange.exceptions.MessageException;
import exchange.currencyexchange.models.Currencies;
import exchange.currencyexchange.repository.CurrenciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServicesImpl implements CurrencyServices {

    private final CurrenciesRepository currenciesRepository;

    @Override
    public CurrencyDTO saveCurrency(String name, String code, String sign) throws MessageException {
        if (code == null || code.isBlank()) {
            throw new MessageException(ErrorMessage.CURRENCY_CODE_EMPTY);
        }

        if (currenciesRepository.existsByCode(code)) {
            throw new MessageException(ErrorMessage.CURRENCY_EXISTS);
        }

        Currencies model = new Currencies();
        model.setCode(code.toUpperCase());
        model.setName(name);
        model.setSign(sign);

        try {
            Currencies savedCurrency = currenciesRepository.save(model);
            return convertToDTO(savedCurrency);
        } catch (DataIntegrityViolationException e) {
            throw new MessageException(ErrorMessage.CURRENCY_SAVE_ERROR);
        } catch (Exception e) {
            throw new MessageException(ErrorMessage.UNKNOWN_ERROR);
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

    @Override
    public CurrencyDTO getCurrencyByCode(String code) throws MessageException {
        if (code == null || code.isBlank()) {
            throw new MessageException(ErrorMessage.CURRENCY_CODE_EMPTY);
        }

        try {
            Currencies currency = currenciesRepository.findByCode(code.toUpperCase());
            if (currency == null) {
                throw new MessageException(ErrorMessage.CURRENCY_NOT_FOUND);
            }
            return convertToDTO(currency);
        } catch (Exception e) {
            throw new MessageException(ErrorMessage.DATABASE_ERROR);
        }
    }

    private CurrencyDTO convertToDTO(Currencies entity) {
        return new CurrencyDTO(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getSign()
        );
    }
}
