package lt.danske.currency.converter.yahoo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class YahooCurrencyHistoryDto {

    private String currencyCode;

    @JsonProperty("quote")
    private List<YahooQuoteDto> quotes;

    public List<YahooQuoteDto> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<YahooQuoteDto> quotes) {
        this.quotes = quotes;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
