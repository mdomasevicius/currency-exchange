package lt.danske.currency.exchange

import com.fasterxml.jackson.databind.ObjectMapper
import lt.danske.currency.exchange.yahoo.YahooFinanceGateway
import spock.lang.Specification
import spock.lang.Unroll

class CurrencyExchangeSpec extends Specification {

    def yahooFinanceGateway = Mock(YahooFinanceGateway)
    def currencyExchangeService = new DefaultCurrencyExchangeService(yahooFinanceGateway, new ObjectMapper())

    @Unroll
    def 'when #amount (#baseCurrency) converted to #targetCurrency with rate of #rate should be #expectedAmount (#targetCurrency)'() {
        given:
            yahooFinanceGateway.retrieveExchangeRate(_, _) >> BigDecimal.valueOf(rate)
        expect:
            expectedAmount == currencyExchangeService.convert(baseCurrency, targetCurrency, amount)
        where:
            baseCurrency | targetCurrency | amount || rate    | expectedAmount
            'USD'        | 'EUR'          | 100    || 0.9     | 90
            'USD'        | 'RUB'          | 150    || 0.5     | 75
            'AUD'        | 'CAD'          | 300    || 2       | 600
            'EUR'        | 'LTL'          | 56844  || 0.93241 | 53001.91
            'NZD'        | 'LTL'          | 33     || 0.9444  | 31.17 //31.1652
    }

    @Unroll
    def 'when user wishes to purchase (#targetCurrency) and has (#amount) of #baseCurrency with rate of #inverseRate - should pay #expectedAmount (#targetCurrency)'() {
        given:
            yahooFinanceGateway.retrieveExchangeRate(_, _) >> BigDecimal.valueOf(inverseRate)
        expect:
            expectedAmount == currencyExchangeService.purchase(baseCurrency, targetCurrency, amount)
        where:
            baseCurrency | targetCurrency | amount || inverseRate | expectedAmount
            'USD'        | 'EUR'          | 100    || 0.9         | 90
            'USD'        | 'RUB'          | 150    || 0.5         | 75
            'AUD'        | 'CAD'          | 300    || 2           | 600
            'EUR'        | 'LTL'          | 56844  || 0.93241     | 53001.91
            'NZD'        | 'LTL'          | 33     || 0.9444      | 31.17 //31.1652
    }

    def 'convert must throw IllegalArgumentException if any argument is null'() {
        when:
            currencyExchangeService.convert(baseCurrency, targetCurrency, amount)
        then:
            thrown IllegalArgumentException
        where:
            baseCurrency | targetCurrency | amount
            null         | 'EUR'          | 100
            'USD'        | null           | 150
            'AUD'        | 'CAD'          | null
    }

    @Unroll
    def 'convert #message'() {
        when:
            currencyExchangeService.purchase(baseCurrency, targetCurrency, 100)
        then:
            thrown ExchangeValidationException
        where:
            baseCurrency | targetCurrency | message
            'X'          | 'EUR'          | 'must throw ExchangeValidationException if baseCurrency is invalid'
            'USD'        | 'X'            | 'must throw ExchangeValidationException if targetCurrency is invalid'
            'Z'          | 'W'            | 'must throw ExchangeValidationException if baseCurrency and targetCurrency are invalid'
    }

    def 'purchase must throw IllegalArgumentException if any argument is null'() {
        when:
            currencyExchangeService.purchase(baseCurrency, targetCurrency, amount)
        then:
            thrown IllegalArgumentException
        where:
            baseCurrency | targetCurrency | amount
            null         | 'EUR'          | 100
            'USD'        | null           | 150
            'AUD'        | 'CAD'          | null
    }

    @Unroll
    def 'purchase #message'() {
        when:
            currencyExchangeService.purchase(baseCurrency, targetCurrency, 100)
        then:
            thrown ExchangeValidationException
        where:
            baseCurrency | targetCurrency | message
            'X'          | 'EUR'          | 'must throw ExchangeValidationException if baseCurrency is invalid'
            'USD'        | 'X'            | 'must throw ExchangeValidationException if targetCurrency is invalid'
            'Z'          | 'W'            | 'must throw ExchangeValidationException if baseCurrency and targetCurrency are invalid'
    }

    def 'must provide common currency codes'() {
        when:
            def commonCurrencies = currencyExchangeService.getCommonCurrencyCodes()
        then:
            commonCurrencies
            commonCurrencies.find()
    }

}
