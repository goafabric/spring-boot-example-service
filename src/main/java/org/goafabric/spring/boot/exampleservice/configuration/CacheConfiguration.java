package org.goafabric.spring.boot.exampleservice.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {
    //names of the different caches, at least one per Resource
    public static final String COUNTRIES = "countries";

    private static final int INITIAL_CACHE_CAPACITY = 100;
    private static final long MAX_CACHE_CAPACITY = 100L;
    private static final long CACHE_EXPIRY = 10L;

    @Bean
    @Override
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(COUNTRIES);
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(INITIAL_CACHE_CAPACITY)
                .maximumSize(MAX_CACHE_CAPACITY)
                .expireAfterAccess(CACHE_EXPIRY, TimeUnit.MINUTES);
    }
}
