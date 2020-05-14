package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import org.goafabric.spring.boot.exampleservice.crossfunctional.TenantRequestContext;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class TenantAware {
    public static final String TENANT_FILTER = "TENANT_FILTER";

    @Access(AccessType.PROPERTY)
    public String getTenantId() {
        return TenantRequestContext.getTenantId(); //this is for save operations only
    }

    public void setTenantId(String tenantId) {
        //should never be set because we alway use the ThreadLocal
    }
}
