package lt.danske.currency.infrastructure;

import lt.danske.currency.exchange.ExchangeValidationException;
import lt.danske.currency.exchange.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    void notFound(Exception ex) {
        log.warn(ex.getMessage(), ex);
    }

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
