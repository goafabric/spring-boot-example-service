package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class TenantAware {
    @Access(AccessType.PROPERTY)
    public String getTenantId() {
        return TenantIdStorage.getTenantId();
    }

    public void setTenantId(String tenantId) {
    }
}
