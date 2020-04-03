package org.goafabric.spring.boot.exampleservice.adapter;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
@Slf4j
@CircuitBreaker(name = "calleeService")
public class CalleeServiceAdapter {
    //@Autowired
    //private CalleService calleService;

    @Autowired
    private RestTemplate adapterRestTemplate;

    public void isAlive() {
        log.info("Calling CalleService ...");
        //restTemplate.getForObject("http://localhost:50800/isAlive")
    }



}
