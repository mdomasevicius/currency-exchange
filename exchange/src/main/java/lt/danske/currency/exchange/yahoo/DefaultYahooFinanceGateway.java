package lt.danske.currency.exchange.yahoo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.danske.currency.exchange.ExchangeValidationException;
import lt.danske.currency.exchange.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.*;
import static org.springframework.http.HttpStatus.OK;

@Service
class DefaultYahooFinanceGateway implements YahooFinanceGateway {
    private static final Logger log = LoggerFactory.getLogger(DefaultYahooFinanceGateway.class);
    private final RestOperations rest;
    private final ObjectMapper mapper;

    @Value("${yahoo-finance.apiUrl}")
    private String apiUrl;

    DefaultYahooFinanceGateway(RestOperations rest, ObjectMapper mapper) {
        this.rest = rest;
        this.mapper = mapper;
    }

    @Cacheable(value = "currency_rates")
    @Override
    public BigDecimal retrieveExchangeRate(String baseCurrency, String targetCurrency) {
        ResponseEntity<JsonNode> response = rest.getForEntity(
            toCurrencyExchangeUri(baseCurrency, targetCurrency),
            JsonNode.class);

        if (response.getStatusCode() != OK) {
            throw ExchangeValidationException.withError(
                "yahooApi",
                "responded with " + response.getStatusCode());
        }

        return getExchangeRateOrFail(response);
    }

    @Cacheable(value = "currency_rates_history")
    @Override
    public YahooCurrencyHistoryDto retrieve10DayExchangeRateHistory(String currencyCode) {
        ResponseEntity<JsonNode> response = rest.getForEntity(
            toCurrencyHistoryUri(
                currencyCode,
                ZonedDateTime.now().minusDays(10),
                ZonedDateTime.now()),
            JsonNode.class);

        return mapHistoricalDataOfFail(response, currencyCode);
    }

    private YahooCurrencyHistoryDto mapHistoricalDataOfFail(ResponseEntity<JsonNode> response, String currencyCode) {
        YahooCurrencyHistoryDto exchangeHistory;
        try {
            exchangeHistory = mapper.treeToValue(
                response.getBody()
                    .path("query")
                    .path("results"),
                YahooCurrencyHistoryDto.class);


        } catch (Throwable e) {
            log.info("could not parse yahoo api response", e);
            throw ExchangeValidationException.withError(
                "yahooApi",
                "could not parse yahoo api response");
        }

        if (exchangeHistory == null) {
            throw new NotFoundException();
        }

        exchangeHistory.setCurrencyCode(currencyCode);
        return exchangeHistory;
    }

    private BigDecimal getExchangeRateOrFail(ResponseEntity<JsonNode> response) {
        BigDecimal result;
        try {
            String rateAsString = response.getBody()
                .path("query")
                .path("results")
                .elements()
                .next()
                .path("Rate")
                .asText();

            result = new BigDecimal(rateAsString);
        } catch (Throwable e) {
            log.info("could not parse yahoo api response", e);
            throw ExchangeValidationException.withError(
                "yahooApi",
                "could not parse yahoo api response");
        }
        return result;
    }

    private URI toCurrencyExchangeUri(String baseCurrency, String targetCurrency) {
        return UriComponentsBuilder.fromHttpUrl(apiUrl)
            .queryParam("q", String.format(
                "select * from " +
                    "yahoo.finance.xchange " +
                    "where pair in (\"%s\")",
                baseCurrency + targetCurrency))
            .queryParam("format", "json")
            .queryParam("diagnostics", "false")
            .queryParam("env",
                "store://datatables.org/alltableswithkeys")
            .build()
            .toUri();
    }

    private URI toCurrencyHistoryUri(String currency, ZonedDateTime from, ZonedDateTime to) {
        String endDate = to.format(ISO_LOCAL_DATE);
        String startDate = from.format(ISO_LOCAL_DATE);

        return UriComponentsBuilder.fromHttpUrl(apiUrl)
            .queryParam("q", String.format(
                "select * from " +
                    "yahoo.finance.historicaldata " +
                    "where symbol=\"%s=X\" " + //currency exchange rate in relation to USD
                    "and startDate=\"%s\" " +
                    "and endDate=\"%s\"", currency, startDate, endDate))
            .queryParam("format", "json")
            .queryParam("diagnostics", "false")
            .queryParam("env",
                "store://datatables.org/alltableswithkeys")
            .build()
            .toUri();
    }
   @CacheEvict(allEntries = true, value = "currency_rates")
    @Scheduled(cron = "${scheduler.caches.currency_rates:0 */5 * * * *}")
    public void clearCurrencyRateCache() {
        log.info("Currency rate cache cleared.");
    }

    @CacheEvict(allEntries = true, value = "currency_rates_history")
    @Scheduled(cron = "${scheduler.caches.currency_rate_history:0 0 0 0 * *}")
    public void clearCurrencyRateHistoryCache() {
        log.info("Currency rate history cache cleared.");
    }
}
