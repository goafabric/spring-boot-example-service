package org.goafabric.spring.boot.exampleservice.persistence.repository;

import org.goafabric.spring.boot.exampleservice.persistence.domain.ConfigurationBo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ConfigurationRepository extends CrudRepository<ConfigurationBo, String> {
}

