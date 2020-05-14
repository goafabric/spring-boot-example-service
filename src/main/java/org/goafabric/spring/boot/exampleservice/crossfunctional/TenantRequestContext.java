package org.goafabric.spring.boot.exampleservice.crossfunctional;

public final class TenantRequestContext {
    private TenantRequestContext() {
    }

    private static ThreadLocal<String> tenantIdThreadLocal = new ThreadLocal<>();

    public static String getTenantId() {
        final String tenantId = tenantIdThreadLocal.get();
        if (tenantId == null) {
            throw new IllegalStateException("tenantId is null");
        }
        return tenantId;
    }

    public static void setTenantId(String tenantId) {
        tenantIdThreadLocal.set(tenantId);
    }

    public static void remove() {
        tenantIdThreadLocal.remove();
    }
}
