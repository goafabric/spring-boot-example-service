package org.goafabric.spring.boot.exampleservice.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.goafabric.spring.boot.exampleservice.persistence.multitenancy.TenantIdStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {
    //names of the different caches, at least one per Resource
    public static final String COUNTRIES = "patients";

    @Value("${cache.maxsize}")
    private Long cacheMaxSize;

    @Value("${cache.expiry}")
    private Long cacheExpiry;

    @Bean
    @Override
    public CacheManager cacheManager() {
        final CaffeineCacheManager cacheManager = new CaffeineCacheManager(COUNTRIES);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(cacheMaxSize)
                .expireAfterAccess(cacheExpiry, TimeUnit.MINUTES));
        return cacheManager;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> new SimpleKey(TenantIdStorage.getTenantId(), params);
    }
}
