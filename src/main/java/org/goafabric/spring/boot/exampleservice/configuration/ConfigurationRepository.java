package org.goafabric.spring.boot.exampleservice.configuration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public interface ConfigurationRepository extends CrudRepository<ConfigurationRepository.ConfigurationBo, String> {

    @Entity
    @Table(name="configuration")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class ConfigurationBo {
        @Id
        private String configKey;
        private String configValue;
    }
}

