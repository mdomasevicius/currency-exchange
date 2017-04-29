package lt.danske.currency.api

import lt.danske.currency.api.client.FluentRestClient
import spock.lang.Shared
import spock.lang.Specification

class ApiIndexSpec extends Specification {

    @Shared
    def rest = FluentRestClient.defaultRestClient()

    def 'must provide API links'() {
        when:
            def response = rest.get('/api')
        then:
            response.status == 200
            response.body._links.size() == 3
    }

}
