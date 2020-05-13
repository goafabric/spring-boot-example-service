package org.goafabric.spring.boot.exampleservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.exampleservice.adapter.RestTemplateFactory;
import org.goafabric.spring.boot.exampleservice.client.TenantIdClientStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Configuration
@PropertySource("test.properties")
public class RestTemplateConfiguration {
    @Value("${client.user}")
    private String user;

    @Value("${client.password}")
    private String password;

    @Bean
    public String serviceBaseUrl(@Value("${service.baseurl}") String serviceBaseUrl) {
        return serviceBaseUrl;
    }

    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = RestTemplateFactory.createRestTemplate(10000, user, password);
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("X-TenantId", TenantIdClientStorage.getTenantId());
            return execution.execute(request, body);
        });
        return restTemplate;
        /*
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return !HttpStatus.OK.equals(clientHttpResponse.getStatusCode());
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                log.error("Error during REST Call {} {}", clientHttpResponse.getStatusCode(), clientHttpResponse.getBody().toString());
            }
        });
         */
    }
}
