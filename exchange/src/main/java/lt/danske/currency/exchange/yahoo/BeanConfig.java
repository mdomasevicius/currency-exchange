package lt.danske.currency.exchange.yahoo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
class BeanConfig {

    @Bean
    RestOperations restOperations() {
        return new RestTemplate();
    }

}
