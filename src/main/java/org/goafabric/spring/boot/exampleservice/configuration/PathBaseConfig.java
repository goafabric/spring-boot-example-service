/*
//needed for multi tenancy
package org.goafabric.spring.boot.exampleservice.configuration;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.spi.HttpFacade;
import org.springframework.stereotype.Component;

@Component
public class PathBaseConfig implements KeycloakConfigResolver {
    @Override
    public KeycloakDeployment resolve(HttpFacade.Request request) {
        //request.getHeader("X-TenantId")
        return null;
    }
}

*/
