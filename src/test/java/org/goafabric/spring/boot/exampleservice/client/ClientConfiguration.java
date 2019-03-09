package org.goafabric.spring.boot.exampleservice.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*
    @Bean
    public CountryServiceClient countryServiceClient(
            @Value("${local.server.port}") String port) {
        return new CountryServiceClient("http://localhost:" + port);
    }
    */
    @Bean
    public CountryServiceClient countryServiceClient() {
        return new CountryServiceClient("http://localhost:50700");
    }
}
