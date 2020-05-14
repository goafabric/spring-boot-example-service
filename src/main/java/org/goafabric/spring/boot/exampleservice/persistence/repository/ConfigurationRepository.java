package org.goafabric.spring.boot.exampleservice.persistence.repository;

import org.goafabric.spring.boot.exampleservice.persistence.domain.ConfigurationBo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

public interface ConfigurationRepository extends CrudRepository<ConfigurationBo, String> {
    @Transactional
    default String getConfigValue(String configKey) {
        final Optional<ConfigurationBo> configuration = this.findById(configKey);
        return configuration.isPresent()
                ? new String (Base64Utils.decodeFromString(configuration.get().getConfigValue()))
                : this.save(ConfigurationBo.builder()
                .configKey(configKey).configValue(
                        Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)))
                .build()).getConfigValue();
    }
}

