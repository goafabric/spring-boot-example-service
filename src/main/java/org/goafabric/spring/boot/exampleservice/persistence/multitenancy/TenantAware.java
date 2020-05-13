package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class TenantAware {
    public static final String WHERE_CLAUSE = "tenant_id = %TENANT_ID%";

    @Access(AccessType.PROPERTY)
    public String getTenantId() {
        return TenantIdStorage.getTenantId();
    }

    public void setTenantId(String tenantId) {
        //should never be set because we alway use the ThreadLocal
    }
}
