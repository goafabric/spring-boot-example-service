package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class TenantAware {
    private String tenantid;
}
