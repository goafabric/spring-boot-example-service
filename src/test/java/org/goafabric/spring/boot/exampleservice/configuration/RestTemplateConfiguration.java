package org.goafabric.spring.boot.exampleservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@Configuration
@PropertySource("test.properties")
public class RestTemplateConfiguration {
    @Value("${client.user}")
    private String user;

    @Value("${client.password}")
    private String password;

    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.getHeaders().setBasicAuth(user, password);
            return execution.execute(request, body);
        });
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

        return restTemplate;
    }
}
