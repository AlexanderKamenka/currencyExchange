package exchange.currencyexchange.exceptions;

import lombok.Getter;

@Getter
public class CurrencyAddException extends Exception {

    private static final String DEFAULT_MESSAGE = "Error adding currency";

    public CurrencyAddException() {
        super(DEFAULT_MESSAGE);
    }

    public CurrencyAddException(String message) {
        super(message);
    }

    public CurrencyAddException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyAddException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
