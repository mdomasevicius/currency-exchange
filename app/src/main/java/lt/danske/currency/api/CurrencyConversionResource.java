package lt.danske.currency.api;

import java.math.BigDecimal;

public class CurrencyConversionResource {

    private final BigDecimal convertedAmount;

    CurrencyConversionResource(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }
}
