package lt.danske.currency.converter;

import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.*;

public class ConverterValidationException extends RuntimeException {

    private final Collection<ValidationError> errors;

    public ConverterValidationException(Collection<ValidationError> errors) {
        this.errors = errors;
    }

    public static ConverterValidationException withError(String context, String message) {
        return new ConverterValidationException(singletonList(new ValidationError(context, message)));
    }

    static class ValidationError {
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
