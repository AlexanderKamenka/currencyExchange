package exchange.currencyexchange.controllers;

import exchange.currencyexchange.dto.ExceptionDto;
import exchange.currencyexchange.dto.ExchangeRateDTO;
import exchange.currencyexchange.exceptions.ErrorMessage;
import exchange.currencyexchange.exceptions.MessageException;
import exchange.currencyexchange.services.ExchangeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final ExchangeServices exchangeServices;

    @PostMapping(consumes = "application/x-www-form-urlencoded", path = "/exchangeRates")
    public ResponseEntity<?> addExchangeRate(@RequestParam String baseCurrencyCode, @RequestParam String targetCurrencyCode, @RequestParam BigDecimal rate) {
        try {
            ExchangeRateDTO exchangeRateDTO = exchangeServices.addExchangeRate(baseCurrencyCode, targetCurrencyCode, rate);
            return ResponseEntity.status(201).body(exchangeRateDTO);
        } catch (MessageException e) {
            ErrorMessage error = e.getErrorMessage();
            return ResponseEntity.status(error.getStatus()).body(new ExceptionDto(error.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(ErrorMessage.UNKNOWN_ERROR.getStatus()).body(new ExceptionDto(ErrorMessage.UNKNOWN_ERROR.getMessage()));
        }
    }

    @GetMapping(path = "/exchangeRates")
    public ResponseEntity<?> getAllExchangeRates() {
        return ResponseEntity.ok(exchangeServices.getExchangeRates());
    }

    @GetMapping(path = "/exchangeRate/{code}")
    public ResponseEntity<?> getExchangeRatesByCurrencies(@PathVariable(required = false) String code) {
        try {
            return ResponseEntity.ok(exchangeServices.getExchangeRatesByCurrencies(code));
        } catch (MessageException e) {
            ErrorMessage error = e.getErrorMessage();
            return ResponseEntity.status(error.getStatus()).body(new ExceptionDto(error.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(ErrorMessage.UNKNOWN_ERROR.getStatus()).body(new ExceptionDto(ErrorMessage.UNKNOWN_ERROR.getMessage()));
        }
    }

    @PatchMapping(consumes = "application/x-www-form-urlencoded", path = "/exchangeRate/{code}")
    public ResponseEntity<?> changeExchangeRateByCurrencies(@PathVariable String code, @RequestParam BigDecimal rate) {
        try {
            return ResponseEntity.ok(exchangeServices.changeExchangeRate(code, rate));
        } catch (MessageException e) {
            ErrorMessage error = e.getErrorMessage();
            return ResponseEntity.status(error.getStatus()).body(new ExceptionDto(error.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(ErrorMessage.UNKNOWN_ERROR.getStatus()).body(new ExceptionDto(ErrorMessage.UNKNOWN_ERROR.getMessage()));
        }
    }
}
