package org.goafabric.spring.boot.exampleservice.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
//@Lazy //Lazy init, in case we need to reencrypt the password (cylic problem during startup)
public class CalleeServiceClient {
    @Autowired
    private RestTemplate calleeServiceRestTemplate;

    @Value("${adapter.calleeservice.url}")
    private String url;

    public Boolean isAlive() {
        return calleeServiceRestTemplate.getForObject(url + "/callees/isAlive", Boolean.class);
    }

    @Bean
    public RestTemplate calleeServiceRestTemplate(
            @Value("${adapter.calleeservice.user}") String user,
            @Value("${adapter.calleeservice.password}") String password,
            @Value("${adapter.timeout}") Integer timeout) {
        return RestTemplateFactory.createRestTemplate(timeout, user, password);
    }
}
