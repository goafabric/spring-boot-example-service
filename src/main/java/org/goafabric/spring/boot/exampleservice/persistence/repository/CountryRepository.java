package org.goafabric.spring.boot.exampleservice.persistence.repository;

import org.goafabric.spring.boot.exampleservice.persistence.domain.CountryBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */

public interface CountryRepository extends JpaRepository<CountryBo, String> {
    List<CountryBo> findAllByTenantId(String tenantId);

    CountryBo findByIdAndTenantId(String id, String tenantId);

    CountryBo findByIsoCode(String isoCode);

    @Query("SELECT c from CountryBo c WHERE name = :name and tenantId = :tenantId")
    CountryBo findByNameAndTenantId(@Param("name") String name, @Param("tenantId") String tenantId);

    CountryBo findBySecretAndTenantId(String secret, String tenantId);

    void deleteByIdAndTenantId(String id, String tenantId);
}
