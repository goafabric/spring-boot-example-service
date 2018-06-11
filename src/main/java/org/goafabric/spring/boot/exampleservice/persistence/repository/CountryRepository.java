package org.goafabric.spring.boot.exampleservice.persistence.repository;

import org.goafabric.spring.boot.exampleservice.configuration.CacheConfiguration;
import org.goafabric.spring.boot.exampleservice.persistence.domain.CountryBO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */
@CacheConfig(cacheNames = {CacheConfiguration.PERSISENCE})
@Cacheable
public interface CountryRepository extends JpaRepository<CountryBO, String> {
    CountryBO findByIsoCode(String isCode);

    @Query("SELECT c from CountryBO c WHERE name = :name") //needs to have -parameters enabled during compile
    CountryBO findByName(String name);
}
