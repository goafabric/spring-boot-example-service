package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import org.goafabric.spring.boot.exampleservice.crossfunctional.TenantStorage;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class TenantAware {
    public static final String TENANT_FILTER = "TENANT_FILTER";

    @Access(AccessType.PROPERTY)
    public String getTenantId() {
        return TenantStorage.getTenantId();
    }

    public void setTenantId(String tenantId) {
        //should never be set because we alway use the ThreadLocal
    }
}
