package org.goafabric.spring.boot.exampleservice.configuration;

import org.goafabric.spring.boot.exampleservice.persistence.multitenancy.TenantIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TenantIdInterceptor());
    }

}