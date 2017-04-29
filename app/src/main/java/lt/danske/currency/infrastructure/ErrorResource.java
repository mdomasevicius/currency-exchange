package lt.danske.currency.infrastructure;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class ErrorResource {
    private String code;
    private String message;
    private List<Item> errors = new ArrayList<>();

    public ErrorResource(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void addErrorItem(String context, String message) {
        errors.add(new Item(context, message));
    }

    public List<Item> getErrors() {
        return unmodifiableList(errors);
    }

    public static class Item {

        private String context;
        private String message;

        public Item(String context, String message) {
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
