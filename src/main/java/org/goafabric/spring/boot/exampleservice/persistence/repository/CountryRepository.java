package org.goafabric.spring.boot.exampleservice.persistence.repository;

import org.goafabric.spring.boot.exampleservice.configuration.CacheConfiguration;
import org.goafabric.spring.boot.exampleservice.persistence.domain.CountryBO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */
@CacheConfig(cacheNames = {CacheConfiguration.PERSISENCE})
@Cacheable
public interface CountryRepository extends JpaRepository<CountryBO, String> {
    CountryBO findByName(String name);
    CountryBO findByIsoCode(String name);

    //@Query("SELECT p from PersonBO p WHERE familyName = :familyName")
    //PersonBO findByFamilyname(@Param("familyName") String familyName);
}
