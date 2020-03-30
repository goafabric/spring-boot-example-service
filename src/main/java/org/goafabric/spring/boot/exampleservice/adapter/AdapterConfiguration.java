package org.goafabric.spring.boot.exampleservice.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class AdapterConfiguration {
    @Value("${adapter.calleeservice.username")
    private String user;

    @Value("${adapter.calleeservice.password")
    private String password;

    @Bean
    public RestTemplate adapterRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            request.getHeaders().setBasicAuth(user, password);
            return execution.execute(request, body);
        });
        return  restTemplate;
    }
}
