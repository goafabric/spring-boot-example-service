package org.goafabric.spring.boot.exampleservice.persistence.repository;

import org.goafabric.spring.boot.exampleservice.persistence.domain.ConfigurationBo;
import org.springframework.data.repository.CrudRepository;

public interface ConfigurationRepository extends CrudRepository<ConfigurationBo, String> {
}