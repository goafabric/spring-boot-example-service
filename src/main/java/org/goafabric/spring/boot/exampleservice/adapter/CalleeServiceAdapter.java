package org.goafabric.spring.boot.exampleservice.adapter;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@CircuitBreaker(name = "calleeservice")
public class CalleeServiceAdapter {
    @Autowired
    @Lazy
    private CalleeServiceClient calleeServiceClient;

    public Boolean isAlive() {
        log.info("Calling CalleService ...");
        final Boolean isAlive = calleeServiceClient.isAlive();
        log.info("got: " + isAlive);
        return isAlive;
    }
}
