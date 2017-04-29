package lt.danske.currency.converter;

import java.util.Collection;

import static java.util.Collections.*;

public class ExchangeValidationException extends RuntimeException {

    private final Collection<ValidationError> errors;

    public ExchangeValidationException(Collection<ValidationError> errors) {
        this.errors = errors;
    }

    public static ExchangeValidationException withError(String context, String message) {
        return new ExchangeValidationException(singletonList(new ValidationError(context, message)));
    }

    public Collection<ValidationError> getErrors() {
        return errors;
    }

    public static class ValidationError {
        private final String context;
        private final String message;

        public ValidationError(String context, String message) {
            this.context = context;
            this.message = message;
        }

        public String getContext() {
            return context;
        }

        public String getMessage() {
            return message;
        }
    }

}
