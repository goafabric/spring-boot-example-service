package org.goafabric.spring.boot.exampleservice;

import org.goafabric.spring.boot.exampleservice.configuration.BaseConfiguration;
import org.springframework.boot.SpringApplication;

/**
 * Created by amautsch on 26.06.2015.
 */

public class Application {
    public static void main(String[] args){
        SpringApplication.run(BaseConfiguration.class, args);
    }
}
