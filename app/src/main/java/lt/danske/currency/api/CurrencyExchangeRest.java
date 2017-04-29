package lt.danske.currency.api;

import lt.danske.currency.converter.CurrencyExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/exchange")
class CurrencyExchangeRest {

    private final CurrencyExchangeService currencyExchangeService;

    CurrencyExchangeRest(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping("/conversion")
    ResponseEntity<CurrencyConversionResource> convert(
        @RequestParam String baseCurrency,
        @RequestParam String targetCurrency,
        @RequestParam BigDecimal amount) {

        BigDecimal convertedAmount = currencyExchangeService.convert(baseCurrency, targetCurrency, amount);
        return ok(new CurrencyConversionResource(convertedAmount));
    }

    @GetMapping("/currencies")
    ResponseEntity<Map<String, String>> getCurrencyCodes() {
        return ok(currencyExchangeService.getCommonCurrencyCodes());
    }

    static class CurrencyConversionResource {

        private final BigDecimal convertedAmount;

        CurrencyConversionResource(BigDecimal convertedAmount) {
            this.convertedAmount = convertedAmount;
        }

        public BigDecimal getConvertedAmount() {
            return convertedAmount;
        }
    }
}
