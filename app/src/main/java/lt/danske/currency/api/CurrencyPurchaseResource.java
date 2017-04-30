package lt.danske.currency.api;

import java.math.BigDecimal;

public class CurrencyPurchaseResource {

    private final BigDecimal requiredAmount;

    public CurrencyPurchaseResource(BigDecimal requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    public BigDecimal getRequiredAmount() {
        return requiredAmount;
    }
}
