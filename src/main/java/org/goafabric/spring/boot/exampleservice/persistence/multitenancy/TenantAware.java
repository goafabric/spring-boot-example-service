package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class TenantAware {
    @Column(name = "tenantid")
    private String tenantId;
}
