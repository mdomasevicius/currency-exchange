package lt.danske.currency.converter

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

@TestConfiguration
class TestConfig {

    @Bean
    @Primary
    RestOperations restOperations() {
        return new RestTemplate()
    }

    @Bean
    MockRestServiceServer mockRestServiceServer(RestOperations restOperations) {
        return MockRestServiceServer.createServer((RestTemplate) restOperations)
    }

}
