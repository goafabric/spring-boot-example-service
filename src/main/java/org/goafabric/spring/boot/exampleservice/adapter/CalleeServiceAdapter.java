package org.goafabric.spring.boot.exampleservice.adapter;

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
public class CalleeServiceAdapter {
    //@Autowired
    //private CalleService calleService;

    @Autowired
    private RestTemplate adapterestTemplate;

    public void isAlive() {
        //restTemplate.getForObject("http://localhost:8080/isAlive")
    }



}
