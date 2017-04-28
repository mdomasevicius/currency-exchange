package lt.danske.currency.api;

import lt.danske.currency.converter.CurrencyConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/currency")
class CurrencyConversionResource {

    private final CurrencyConverterService currencyConverterService;

    CurrencyConversionResource(CurrencyConverterService currencyConverterService) {
        this.currencyConverterService = currencyConverterService;
    }

    @GetMapping("/conversion")
    ResponseEntity convert(
        @RequestParam String baseCurrency,
        @RequestParam String targetCurrency,
        @RequestParam BigDecimal amount) {

        BigDecimal convertedAmount = currencyConverterService.convert(baseCurrency, targetCurrency, amount);
        return ResponseEntity.ok(new CurrencyConversionRepresentation(convertedAmount));
    }

    static class CurrencyConversionRepresentation {

        private final BigDecimal convertedAmount;

        CurrencyConversionRepresentation(BigDecimal convertedAmount) {
            this.convertedAmount = convertedAmount;
        }

        public BigDecimal getConvertedAmount() {
            return convertedAmount;
        }
    }
}