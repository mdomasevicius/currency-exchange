package lt.danske.currency.exchange;

import java.math.BigDecimal;

public class QuoteDto {

    private String date;
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
