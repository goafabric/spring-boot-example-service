package org.goafabric.spring.boot.exampleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.data.RepositoryMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

/**
 * Created by amautsch on 26.06.2015.
 */

@SpringBootApplication(exclude =
        { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class,
                RepositoryMetricsAutoConfiguration.class}) //workaround for metrics problem in spring boot 2.5
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
