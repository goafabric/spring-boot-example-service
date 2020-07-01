package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TenantInspectorTest {
    @Test
    public void testInspect() {
        TenantInspector tenantInspector = new TenantInspector();
        assertThat(tenantInspector.inspect("")).isNotNull();

    }
}