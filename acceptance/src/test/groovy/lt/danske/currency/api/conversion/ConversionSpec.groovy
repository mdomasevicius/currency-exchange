package lt.danske.currency.api.conversion

import lt.danske.currency.api.client.FluentRestClient
import spock.lang.Shared
import spock.lang.Specification

class ConversionSpec extends Specification {

    @Shared
    def rest = FluentRestClient.defaultRestClient()

    def 'user can convert currencies'() {
        when:
            def response = rest.get(
                '/api/currency/conversion',
                [
                    baseCurrency  : 'EUR',
                    targetCurrency: 'USD',
                    amount        : 100
                ])
        then:
            with(response) {
                it.status == 200
                it.body.convertedAmount
            }
    }

    def 'all query parameters are required'() {
        expect:
            rest.get('/api/currency/conversion').status == expectedResponse
        where:
            queryParams                                  || expectedResponse
            [targetCurrency: 'USD', amount: 100]         || 400
            [baseCurrency: 'EUR', amount: 100]           || 400
            [baseCurrency: 'EUR', targetCurrency: 'USD'] || 400
    }

}
