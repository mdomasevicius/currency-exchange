package lt.danske.currency.api.exchange

import lt.danske.currency.api.client.FluentRestClient
import spock.lang.Shared
import spock.lang.Specification

class CurrencyExchangeRestSpec extends Specification {

    @Shared
    def rest = FluentRestClient.defaultRestClient()

    def 'user can convert currencies'() {
        when:
            def response = rest.get(
                '/api/exchange/conversion',
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

    def 'user can purchase currencies'() {
        when:
            def response = rest.get(
                '/api/exchange/purchase',
                [
                    baseCurrency  : 'EUR',
                    targetCurrency: 'USD',
                    amount        : 100
                ])
        then:
            with(response) {
                it.status == 200
                it.body.requiredAmount
            }
    }

    def 'all query parameters for conversion are required'() {
        expect:
            rest.get('/api/exchange/conversion').status == expectedResponse
        where:
            queryParams                                  || expectedResponse
            [targetCurrency: 'USD', amount: 100]         || 400
            [baseCurrency: 'EUR', amount: 100]           || 400
            [baseCurrency: 'EUR', targetCurrency: 'USD'] || 400
    }

    def 'all query parameters for purchase are required'() {
        expect:
            rest.get('/api/exchange/purchase').status == expectedResponse
        where:
            queryParams                                  || expectedResponse
            [targetCurrency: 'USD', amount: 100]         || 400
            [baseCurrency: 'EUR', amount: 100]           || 400
            [baseCurrency: 'EUR', targetCurrency: 'USD'] || 400
    }

    def 'use can get common currency codes'() {
        when:
            def response = rest.get('/api/exchange/currencies')
        then:
            with(response) {
                it.status == 200
                it.body
                it.body.find { it.code == 'EUR' } //check some of the most common currencies
                it.body.find { it.code == 'USD' }
            }
    }

    def 'invalid currency code produces pretty response'() {
        when:
            def response = rest.get('/api/exchange/conversion',
                [
                    baseCurrency  : '32',
                    targetCurrency: '12',
                    amount        : 100
                ])
        then:
            response.status == 400
            with(response.body) {
                it.errors.size() > 1
                it.errors.find { it.context == 'baseCurrency' }
                it.errors.find { it.context == 'targetCurrency' }
            }
    }

    def 'user can retrieve currency exchange rate history'() {
        when:
            def response = rest.get('/api/exchange/history', [currencyCode: 'EUR'])
        then:
            response.status == 200
            with(response.body) {
                it.currencyCode == 'EUR'
                it.quotes
            }
    }

    def 'legacy currencies may be not found'() {
        when:
            def response = rest.get('/api/exchange/history', [currencyCode: 'LTL'])
        then:
            response.status == 404
    }


}
