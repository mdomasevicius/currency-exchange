package lt.danske.currency.exchange.yahoo;

import java.math.BigDecimal;

public interface YahooFinanceGateway {

    BigDecimal retrieveExchangeRate(String baseCurrency, String targetCurrency);

    YahooCurrencyHistoryDto retrieve10DayExchangeRateHistory(String currencyCode);

    void clearCurrencyRateCache();

    void clearCurrencyRateHistoryCache();

}
