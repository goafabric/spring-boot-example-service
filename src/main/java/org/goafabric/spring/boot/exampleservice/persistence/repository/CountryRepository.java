package org.goafabric.spring.boot.exampleservice.persistence.repository;

import org.goafabric.spring.boot.exampleservice.persistence.domain.CountryBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */
//@CacheConfig(cacheNames = {CacheConfiguration.PERSISENCE})
//@Cacheable
public interface CountryRepository extends JpaRepository<CountryBO, String> {
    CountryBO findByIsoCode(String isoCode);

    @Query("SELECT c from CountryBO c WHERE name = :name") //needs to have -parameters enabled during compile
    CountryBO findByName(@Param("name") String name);
}
