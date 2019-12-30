package org.goafabric.spring.boot.exampleservice.persistence.repository;

import org.goafabric.spring.boot.exampleservice.configuration.CacheConfiguration;
import org.goafabric.spring.boot.exampleservice.persistence.domain.CountryBo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */
@CacheConfig(cacheNames = {CacheConfiguration.PERSISENCE})
public interface CountryRepository extends JpaRepository<CountryBo, String> {
    @Cacheable
    CountryBo findByIsoCode(String isoCode);

    @Cacheable
    @Query("SELECT c from CountryBo c WHERE name = :name")
    CountryBo findByName(@Param("name") String name);
}
