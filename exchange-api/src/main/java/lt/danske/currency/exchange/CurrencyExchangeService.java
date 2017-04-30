package lt.danske.currency.exchange;

import java.math.BigDecimal;
import java.util.Map;

public interface CurrencyExchangeService {

    BigDecimal convert(String baseCurrency, String targetCurrency, BigDecimal amount);

    BigDecimal purchase(String baseCurrency, String targetCurrency, BigDecimal amount);

    Map<String, String> getCommonCurrencyCodes();

    CurrencyHistoryDto findCurrencyRateHistory(String currencyCode);
}
