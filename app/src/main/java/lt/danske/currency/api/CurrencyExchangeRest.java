package lt.danske.currency.api;

import lt.danske.currency.exchange.CurrencyExchangeService;
import lt.danske.currency.exchange.CurrencyHistoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/exchange")
public class CurrencyExchangeRest {

    private final CurrencyExchangeService currencyExchangeService;

    CurrencyExchangeRest(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping("/conversion")
    public ResponseEntity<CurrencyConversionResource> convert(
        @RequestParam String baseCurrency,
        @RequestParam String targetCurrency,
        @RequestParam BigDecimal amount) {

        BigDecimal convertedAmount = currencyExchangeService.convert(baseCurrency, targetCurrency, amount);
        return ok(new CurrencyConversionResource(convertedAmount));
    }

    @GetMapping("/purchase")
    public ResponseEntity<CurrencyPurchaseResource> purchase(
        @RequestParam String baseCurrency,
        @RequestParam String targetCurrency,
        @RequestParam BigDecimal amount
    ) {
        BigDecimal requiredAmount = currencyExchangeService.purchase(baseCurrency, targetCurrency, amount);
        return ok(new CurrencyPurchaseResource(requiredAmount));
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<CurrencyCodeResource>> getAvailableCurrencyCodes() {
        return ok(CurrencyCodeResource.fromMap(currencyExchangeService.getCommonCurrencyCodes()));
    }

    @GetMapping("/history")
    public ResponseEntity<CurrencyHistoryDto> get10DayExchangeRateHistory(@RequestParam String currencyCode) {
        return ok(currencyExchangeService.findCurrencyRateHistory(currencyCode));
    }

}
