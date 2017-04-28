package lt.danske.currency.converter;

import java.math.BigDecimal;

public interface CurrencyConverterService {

    BigDecimal convert(String baseCurrency, String targetCurrency, BigDecimal amount);

}
