package lt.danske.currency.converter.yahoo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class YahooQuoteDto {

    @JsonProperty("Date")
    private String date;
    @JsonProperty("High")
    private BigDecimal high;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }
}
