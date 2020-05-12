package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;

@Slf4j
public class TenantInspector implements StatementInspector {
    @Override
    public String inspect(String sql) { //inspect sqls from entities annotated with @WHERE(".tenantId = '%TENANT_ID%'")
        if (sql.contains(".tenantId = '%TENANT_ID%'")) {
            sql = sql.replace(".tenantId = '%TENANT_ID%'", ".tenantId = '" + TenantIdStorage.getTenantId() + "'");
        }
        log.info(sql);
        return sql;
    }
}
