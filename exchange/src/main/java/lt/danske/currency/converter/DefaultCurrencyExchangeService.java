package lt.danske.currency.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.danske.currency.converter.yahoo.YahooFinanceGateway;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
class DefaultCurrencyExchangeService implements CurrencyExchangeService {

    private final Logger log = LoggerFactory.getLogger(DefaultCurrencyExchangeService.class);
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

        BigDecimal rate = yahooFinanceGateway.retrieveExchangeRate(baseCurrency, targetCurrency);
        return amount.multiply(rate)
            .setScale(2, ROUND_HALF_UP);
    }

    @Override
    public Map<String, String> getCommonCurrencyCodes() {
        Map<String, String> currencyMap;

        try {
            currencyMap = objectMapper.readValue(
                IOUtils.toByteArray(this.getClass()
                    .getResourceAsStream("/currencies.json")),
                new TypeReference<HashMap<String, String>>() {});
        } catch (Throwable e) {
            log.error("Could not load currency list.", e);
            throw ConverterValidationException.withError("currencies.json", "failed to load");
        }
        return currencyMap;
    }


}
