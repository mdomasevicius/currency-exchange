package lt.danske.currency.converter;

import lt.danske.currency.converter.yahoo.YahooFinanceGateway;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;

@Service
class DefaultCurrencyConverterService implements CurrencyConverterService {

    private final YahooFinanceGateway yahooFinanceGateway;

    DefaultCurrencyConverterService(YahooFinanceGateway yahooFinanceGateway) {
        this.yahooFinanceGateway = yahooFinanceGateway;
    }

    @Override
    public BigDecimal convert(String baseCurrency, String targetCurrency, BigDecimal amount) {
        Assert.notNull(baseCurrency, "baseCurrency can not be null");
        Assert.notNull(targetCurrency, "targetCurrency can not be null");
        Assert.notNull(amount, "amount can not be null");

        BigDecimal rate = yahooFinanceGateway.retrieveExchangeRate(baseCurrency, targetCurrency);
        return amount.multiply(rate)
            .setScale(2, ROUND_HALF_UP);
    }
}
