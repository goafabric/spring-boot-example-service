package org.goafabric.spring.boot.exampleservice.adapter;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Lazy //Lazy init, in case we need to reencrypt the password (cylic problem during startup)
@Slf4j
@CircuitBreaker(name = "calleeservice")
public class CalleeServiceAdapter {
    @Autowired
    private RestTemplate calleeServiceAdapterRestTemplate;

    @Value("${adapter.calleeservice.url}")
    private String url;

    public Boolean isAlive() {
        log.info("Calling CalleService ...");
        final Boolean isAlive = calleeServiceAdapterRestTemplate.getForObject(url + "callees/isAlive", Boolean.class);
        log.info("got: " + isAlive);
        return isAlive;
    }

    @Bean
    public RestTemplate calleeServiceAdapterRestTemplate(
            @Value("${adapter.calleeservice.user}") String user,
            @Value("${adapter.calleeservice.password}") String password,
            @Value("${adapter.timeout}") Integer timeout) {
        return RestTemplateFactory.createRestTemplate(timeout, user, password);
    }
}
