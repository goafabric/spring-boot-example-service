package org.goafabric.spring.boot.exampleservice.persistence.repository;

import org.goafabric.spring.boot.exampleservice.configuration.CacheConfiguration;
import org.goafabric.spring.boot.exampleservice.persistence.domain.CountryBo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */

@CacheConfig(cacheNames = {CacheConfiguration.COUNTRIES})
public interface CountryRepository extends JpaRepository<CountryBo, String> {

    List<CountryBo> findAllByTenantId(String tenantId);

    @Cacheable
    CountryBo findByIdAndTenantId(String id, String tenantId);

    @Cacheable
    CountryBo findByIsoCodeAndTenantId(String isoCode, String tenantId);

    @Cacheable
    @Query("SELECT c from CountryBo c WHERE name = :name and tenantId = :tenantId")
    CountryBo findByNameAndTenantId(@Param("name") String name, @Param("tenantId") String tenantId);

    @Cacheable
    CountryBo findBySecretAndTenantId(String secret, String tenantId);

    @CacheEvict(allEntries = true)
    void deleteByIdAndTenantId(String id, String tenantId);

    //@CacheEvict(allEntries = true)
    //CountryBo save(CountryBo countryBo);
}
