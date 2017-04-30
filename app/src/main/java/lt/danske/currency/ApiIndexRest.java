package lt.danske.currency;

import lt.danske.currency.api.CurrencyExchangeRest;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import static org.springframework.http.ResponseEntity.*;

@RestController
public class ApiIndexRest {

    @GetMapping("/api")
    ResponseEntity<ApiIndexResource> index() {
        ApiIndexResource index = new ApiIndexResource();
        index.add(linkTo(methodOn(CurrencyExchangeRest.class).convert(null, null, null))
            .withRel("convert"));
        index.add(linkTo(methodOn(CurrencyExchangeRest.class).purchase(null, null, null))
            .withRel("purchase"));
        index.add(linkTo(methodOn(CurrencyExchangeRest.class).getAvailableCurrencyCodes())
            .withRel("availableCurrencies"));
        index.add(linkTo(methodOn(CurrencyExchangeRest.class).get10DayExchangeRateHistory(null))
            .withRel("10DayExchangeRateHistory"));
        return ok(index);
    }

    static class ApiIndexResource extends ResourceSupport {
    }
}
