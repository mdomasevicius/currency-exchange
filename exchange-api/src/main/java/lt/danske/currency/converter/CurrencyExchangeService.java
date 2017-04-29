package lt.danske.currency.converter;

import java.math.BigDecimal;
import java.util.Map;

public interface CurrencyExchangeService {

    BigDecimal convert(String baseCurrency, String targetCurrency, BigDecimal amount);

    Map<String, String> getCommonCurrencyCodes();

    CurrencyHistoryDto findCurrencyRateHistory(String currencyCode);
}
