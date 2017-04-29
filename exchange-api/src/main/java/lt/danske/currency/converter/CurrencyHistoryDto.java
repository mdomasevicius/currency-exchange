package lt.danske.currency.converter;

import java.util.List;

public class CurrencyHistoryDto {

    private String currencyCode;
    private List<QuoteDto> quotes;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public List<QuoteDto> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<QuoteDto> quotes) {
        this.quotes = quotes;
    }
}
