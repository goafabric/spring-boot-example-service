package org.goafabric.spring.boot.exampleservice.persistence.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.goafabric.spring.boot.exampleservice.persistence.domain.ConfigurationBo;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public interface ConfigurationRepository extends CrudRepository<ConfigurationBo, String> {
}

