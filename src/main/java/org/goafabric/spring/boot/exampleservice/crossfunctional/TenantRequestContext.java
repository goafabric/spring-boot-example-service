package org.goafabric.spring.boot.exampleservice.crossfunctional;

public final class TenantRequestContext {
    private TenantRequestContext() {
    }

    private static final ThreadLocal<String> tenantIdThreadLocal = new ThreadLocal<>();

    public static String getTenantId() {
        String tenantId = tenantIdThreadLocal.get();
        if (tenantId == null) {
            tenantId = "10"; //TODO: warning this is just a temp hack to make the demo frontend work without http headders
            //throw new IllegalStateException("tenantId is null");
        }
        return tenantId;
    }

    static void setTenantId(String tenantId) {
        tenantIdThreadLocal.set(tenantId);
    }

    static void remove() {
        tenantIdThreadLocal.remove();
    }
}
