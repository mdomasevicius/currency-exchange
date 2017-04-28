package lt.danske.currency.converter

import lt.danske.currency.converter.yahoo.YahooFinanceGateway
import spock.lang.Specification
import spock.lang.Unroll

class CurrencyConverterSpec extends Specification {

    def yahooFinanceGateway = Mock(YahooFinanceGateway)
    def currencyConverterService = new DefaultCurrencyConverterService(yahooFinanceGateway)

    @Unroll
    def 'when #amount (#baseCurrency) converted to #targetCurrency with rate of #rate should be #expectedAmount'() {
        given:
            yahooFinanceGateway.retrieveExchangeRate(_, _) >> BigDecimal.valueOf(rate)
        expect:
            expectedAmount == currencyConverterService.convert(baseCurrency, targetCurrency, amount)
        where:
            baseCurrency | targetCurrency | amount || rate    | expectedAmount
            'USD'        | 'EUR'          | 100    || 0.9     | 90
            'USD'        | 'RUB'          | 150    || 0.5     | 75
            'AUD'        | 'CAD'          | 300    || 2       | 600
            'EUR'        | 'LTL'          | 56844  || 0.93241 | 53001.91
            'NZD'        | 'LTL'          | 33     || 0.9444  | 31.17 //31.1652
    }

    def 'should throw IllegalArgumentException if any argument is null'() {
        when:
            yahooFinanceGateway.retrieveExchangeRate(_, _) >> BigDecimal.valueOf(1)
            currencyConverterService.convert(baseCurrency, targetCurrency, amount)
        then:
            thrown IllegalArgumentException
        where:
            baseCurrency | targetCurrency | amount
            null         | 'EUR'          | 100
            'USD'        | null           | 150
            'AUD'        | 'CAD'          | null
    }

}
