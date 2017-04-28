package lt.danske.currency.converter;

import java.math.BigDecimal;

public interface CurrencyExchangeService {

    BigDecimal convert(String baseCurrency, String targetCurrency, BigDecimal amount);

}
