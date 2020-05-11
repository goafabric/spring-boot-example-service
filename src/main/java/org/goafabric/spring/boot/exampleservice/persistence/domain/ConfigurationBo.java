package org.goafabric.spring.boot.exampleservice.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="configuration")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationBo {
    @Id
    private String configKey;
    private String configValue;
}