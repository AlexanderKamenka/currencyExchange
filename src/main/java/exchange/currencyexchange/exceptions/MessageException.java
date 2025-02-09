package exchange.currencyexchange.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageException extends Exception {

    private final ErrorMessage errorMessage;

}
