package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;

@Slf4j
public class TenantInspector implements StatementInspector {
    @Override
    public String inspect(String sql) {
        sql = sql.replace(".tenantId = '%TENANT_ID%'", ".tenantId = '11'");
        log.info(sql);
        return sql;
    }
}
