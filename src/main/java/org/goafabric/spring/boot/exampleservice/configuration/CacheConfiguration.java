package org.goafabric.spring.boot.exampleservice.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {
    //names of the different caches, at least one per concern
    public static final String PERSISENCE = "persistence";

    private static final int INITIAL_CACHE_CAPACITY = 100;
    private static final long MAX_CACHE_CAPACITY = 1000L;
    private static final long CACHE_EXPIRY = 60L;

    @Bean
    @Override
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(PERSISENCE);
        cacheManager.setAllowNullValues(false);
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(INITIAL_CACHE_CAPACITY)
                .maximumSize(MAX_CACHE_CAPACITY)
                .expireAfterAccess(CACHE_EXPIRY, TimeUnit.MINUTES);
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new ListCacheKeyGenerator();
    }

    private static class ListCacheKeyGenerator implements KeyGenerator {
        public Object generate(Object target, Method method, Object... params) {
            return createKey(method.getDeclaringClass().getSimpleName(), method.getName(),
                    params);
        }

        public static List<Object> createKey(
                final String className, final String methodName,  Object... params) {
            final List<Object> key = new ArrayList<>();
            key.add(className);
            key.add(methodName);
            key.addAll(Arrays.asList(params));
            return key;
        }
    }
}
