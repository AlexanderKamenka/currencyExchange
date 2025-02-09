package exchange.currencyexchange.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    CURRENCY_CODE_EMPTY("The currency code is missing from the address", HttpStatus.BAD_REQUEST),
    CURRENCY_EXISTS("The currency with this code already exists.", HttpStatus.CONFLICT),
    CURRENCY_SAVE_ERROR("Currency saving error: violation of uniqueness.", HttpStatus.INTERNAL_SERVER_ERROR),
    CURRENCY_NOT_FOUND("Currency not found", HttpStatus.NOT_FOUND),
    MISSING_REQUIRED_FIELDS("Missing required fields in the request", HttpStatus.BAD_REQUEST),
    DATABASE_ERROR("Database error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_ERROR("Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR),

    EXCHANGE_RATE_EXISTS("Exchange rate for the given currency pair already exists.", HttpStatus.CONFLICT),
    EXCHANGE_RATE_NOT_FOUND("Exchange rate not found for the given currency pair.", HttpStatus.NOT_FOUND),
    EXCHANGE_RATE_SAVE_ERROR("Error occurred while saving the exchange rate.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CURRENCY_CODE("Invalid currency code format. Expected 6 letters", HttpStatus.BAD_REQUEST),
    EXCHANGE_RATE_CHANGE_ERROR("Error occurred while updating the exchange rate.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;
}
