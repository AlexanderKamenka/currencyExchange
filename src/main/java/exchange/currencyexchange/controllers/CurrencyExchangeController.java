package exchange.currencyexchange.controllers;

import exchange.currencyexchange.dto.CurrencyDTO;
import exchange.currencyexchange.exceptions.CurrencyAddException;
import exchange.currencyexchange.services.CurrencyServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final CurrencyServices currencyServices;

    @PostMapping(consumes = "application/x-www-form-urlencoded", path = "/currencies")
    public ResponseEntity<?> addCurrency(
            @RequestParam String name,
            @RequestParam String code,
            @RequestParam String sign) {
        try {
            CurrencyDTO currencyDTO = currencyServices.saveCurrency(name, code, sign);
            return new ResponseEntity<>(currencyDTO, HttpStatus.CREATED);
        } catch (CurrencyAddException e) {
            return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/currencies")
    public ResponseEntity<List<CurrencyDTO>> getCurrencies() {
        return ResponseEntity.ok(currencyServices.getCurrencies());
    }

}
