package lt.danske.currency.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

class CurrencyCodeResource {
    private String code;
    private String name;

    CurrencyCodeResource(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    static List<CurrencyCodeResource> fromMap(Map<String, String> commonCurrencies) {
        return commonCurrencies.entrySet()
            .stream()
            .map(entry -> new CurrencyCodeResource(entry.getKey(), entry.getValue()))
            .collect(toList());
    }
}
