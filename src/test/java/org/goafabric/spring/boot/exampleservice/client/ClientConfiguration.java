package org.goafabric.spring.boot.exampleservice.client;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            return execution.execute(request, body);
        });

        return restTemplate;
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
        return new CountryServiceClient("http://localhost:8080");
    }
}
