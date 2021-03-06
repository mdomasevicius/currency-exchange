package lt.danske.currency.exchange;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.danske.currency.exchange.yahoo.YahooCurrencyHistoryDto;
import lt.danske.currency.exchange.yahoo.YahooFinanceGateway;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.util.stream.Collectors.toList;
import static lt.danske.currency.exchange.ExchangeValidationException.ValidationError;
import static lt.danske.currency.exchange.ExchangeValidationException.withError;

@Service
class DefaultCurrencyExchangeService implements CurrencyExchangeService {

    private static final Logger log = LoggerFactory.getLogger(DefaultCurrencyExchangeService.class);
    private final YahooFinanceGateway yahooFinanceGateway;
    private final ObjectMapper objectMapper;

    DefaultCurrencyExchangeService(YahooFinanceGateway yahooFinanceGateway, ObjectMapper objectMapper) {
        this.yahooFinanceGateway = yahooFinanceGateway;
        this.objectMapper = objectMapper;
    }

    @Override
    public BigDecimal convert(String baseCurrency, String targetCurrency, BigDecimal amount) {
        Assert.notNull(baseCurrency, "baseCurrency can not be null");
        Assert.notNull(targetCurrency, "targetCurrency can not be null");
        Assert.notNull(amount, "amount can not be null");

        validateCurrencyCodes(baseCurrency, targetCurrency);

        BigDecimal rate = yahooFinanceGateway.retrieveExchangeRate(baseCurrency, targetCurrency);
        return amount.multiply(rate)
            .setScale(2, ROUND_HALF_UP);
    }

    @Override
    public BigDecimal purchase(String baseCurrency, String targetCurrency, BigDecimal amount) {
        Assert.notNull(baseCurrency, "baseCurrency can not be null");
        Assert.notNull(targetCurrency, "targetCurrency can not be null");
        Assert.notNull(amount, "amount can not be null");

        validateCurrencyCodes(baseCurrency, targetCurrency);

        BigDecimal inverseRate = yahooFinanceGateway.retrieveExchangeRate(targetCurrency, baseCurrency);
        return amount.multiply(inverseRate)
            .setScale(2, ROUND_HALF_UP);
    }

    private void validateCurrencyCodes(String baseCurrency, String targetCurrency) {
        Map<String, String> commonCurrencyCodes = getCommonCurrencyCodes();
        ArrayList<ValidationError> validationErrors = new ArrayList<>();
        if (!commonCurrencyCodes.containsKey(baseCurrency)) {
            validationErrors.add(new ValidationError("baseCurrency", "invalid"));
        }
        if (!commonCurrencyCodes.containsKey(targetCurrency)) {
            validationErrors.add(new ValidationError("targetCurrency", "invalid"));
        }
        if (validationErrors.size() > 0) {
            throw new ExchangeValidationException(validationErrors);
        }
    }

    @Override
    public Map<String, String> getCommonCurrencyCodes() {
        Map<String, String> currencyMap;

        try {
            currencyMap = objectMapper.readValue(
                IOUtils.toByteArray(this.getClass()
                    .getResourceAsStream("/currencies.json")),
                new TypeReference<TreeMap<String, String>>() {});
        } catch (Throwable e) {
            log.error("Could not load currency list.", e);
            throw withError("currencies.json", "failed to load");
        }
        return currencyMap;
    }

    @Override
    public CurrencyHistoryDto findCurrencyRateHistory(String currencyCode) {
        if (!getCommonCurrencyCodes().containsKey(currencyCode)) {
            throw ExchangeValidationException.withError("currencyCode", "invalid");
        }

        YahooCurrencyHistoryDto yahooCurrencyHistory = yahooFinanceGateway.retrieve10DayExchangeRateHistory(currencyCode);

        CurrencyHistoryDto currencyHistoryDto = new CurrencyHistoryDto();
        currencyHistoryDto.setCurrencyCode(yahooCurrencyHistory.getCurrencyCode());
        currencyHistoryDto.setQuotes(
            yahooCurrencyHistory.getQuotes()
            .stream()
            .map(yahooQuote -> {
                QuoteDto quote = new QuoteDto();
                quote.setDate(yahooQuote.getDate());
                quote.setHigh(yahooQuote.getHigh());
                return quote;
            }).collect(toList())
        );
        return currencyHistoryDto;
    }


}
