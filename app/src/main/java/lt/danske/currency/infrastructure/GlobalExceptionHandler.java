package lt.danske.currency.infrastructure;

import lt.danske.currency.converter.ExchangeValidationException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ExchangeValidationException.class)
    ErrorResource converterValidationException(ExchangeValidationException ex) {
        ErrorResource error = new ErrorResource("VALIDATION_FAILED", "Request validation failed");
        ex.getErrors().forEach(err ->
            error.addErrorItem(err.getContext(), err.getMessage())
        );
        return error;
    }

}
