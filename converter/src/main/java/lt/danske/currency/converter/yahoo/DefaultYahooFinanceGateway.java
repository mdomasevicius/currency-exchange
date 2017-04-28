package lt.danske.currency.converter.yahoo;

import com.fasterxml.jackson.databind.JsonNode;
import lt.danske.currency.converter.ConverterValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

import static org.springframework.http.HttpStatus.OK;

@Service
class DefaultYahooFinanceGateway implements YahooFinanceGateway {
    private static final Logger log = LoggerFactory.getLogger(DefaultYahooFinanceGateway.class);
    private final RestOperations rest;

    @Value("${yahoo-finance.apiUrl}")
    private String apiUrl;

    public DefaultYahooFinanceGateway(RestOperations rest) {
        this.rest = rest;
    }

    @Override
    public BigDecimal retrieveExchangeRate(String baseCurrency, String targetCurrency) {
        ResponseEntity<JsonNode> response = rest.getForEntity(
            toCurrencyExchangeUri(baseCurrency, targetCurrency),
            JsonNode.class);

        if (response.getStatusCode() != OK) {
            throw ConverterValidationException.withError(
                "yahooApi",
                "responded with " + response.getStatusCode());
        }

        return mapOrFail(response);
    }

    private BigDecimal mapOrFail(ResponseEntity<JsonNode> response) {
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
            throw ConverterValidationException.withError(
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
}
