package org.goafabric.spring.boot.exampleservice.configuration;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.goafabric.spring.boot.exampleservice.service.CountryServiceBean;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/countryservice_1_0")
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {
        register(CountryServiceBean.class);
        register(JacksonFeature.class);
    }
}
