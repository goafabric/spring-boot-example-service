package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import lombok.NonNull;

public class TenantIdStorage {
    private static ThreadLocal<String> tenantIdThreadLocal = new ThreadLocal<>();

    public static String getTenantId() {
        final String tenantId = tenantIdThreadLocal.get();
        //final String tenantId = "11";
        if (tenantId == null) throw new IllegalStateException("tenantId is null");
        return tenantId;
    }

    public static void setTenantId(String tenantId) {
        tenantIdThreadLocal.set(tenantId);
    }
}
