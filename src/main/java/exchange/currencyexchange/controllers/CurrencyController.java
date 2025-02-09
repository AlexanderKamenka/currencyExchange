package exchange.currencyexchange.controllers;

import exchange.currencyexchange.dto.CurrencyDTO;
import exchange.currencyexchange.dto.ExceptionDto;
import exchange.currencyexchange.exceptions.ErrorMessage;
import exchange.currencyexchange.exceptions.MessageException;
import exchange.currencyexchange.services.CurrencyServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyServices currencyServices;

    @PostMapping(consumes = "application/x-www-form-urlencoded", path = "/currencies")
    public ResponseEntity<?> addCurrency(@RequestParam String code, @RequestParam String name, @RequestParam String sign) {
        try {
            CurrencyDTO currencyDTO = currencyServices.saveCurrency(name, code, sign);
            return ResponseEntity.status(201).body(currencyDTO);
        } catch (MessageException e) {
            ErrorMessage error = e.getErrorMessage();
            return ResponseEntity.status(error.getStatus()).body(new ExceptionDto(error.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(ErrorMessage.UNKNOWN_ERROR.getStatus()).body(new ExceptionDto(ErrorMessage.UNKNOWN_ERROR.getMessage()));
        }
    }

    @GetMapping(path = "/currencies")
    public ResponseEntity<List<CurrencyDTO>> getCurrencies() {
        return ResponseEntity.ok(currencyServices.getCurrencies());
    }

    @GetMapping(path = "/currency/{code}")
    public ResponseEntity<?> getCurrency(@PathVariable(required = false) String code) {
        if (code == null || code.isEmpty()) {
            return ResponseEntity.status(ErrorMessage.CURRENCY_CODE_EMPTY.getStatus()).body(new ExceptionDto(ErrorMessage.CURRENCY_CODE_EMPTY.getMessage()));
        }
        try {
            CurrencyDTO currency = currencyServices.getCurrencyByCode(code);
            return ResponseEntity.ok(currency);
        } catch (MessageException e) {
            ErrorMessage error = e.getErrorMessage();
            return ResponseEntity.status(error.getStatus()).body(new ExceptionDto(error.getMessage()));  // Ответ с ошибкой
        } catch (Exception e) {
            return ResponseEntity.status(ErrorMessage.UNKNOWN_ERROR.getStatus()).body(new ExceptionDto(ErrorMessage.UNKNOWN_ERROR.getMessage()));  // Общая ошибка
        }
    }
}
