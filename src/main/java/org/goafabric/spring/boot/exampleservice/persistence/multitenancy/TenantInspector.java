package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;

@Slf4j
public class TenantInspector implements StatementInspector {
    //inspect sqls from entities annotated with @WHERE(".tenantId = %TENANT_ID%")
    //we intentionally ommit the ' inside the @WHERE, because if the replacement will not take place, the sql preparation will crash
    @Override
    public String inspect(String sql) {
        if (sql.contains(".tenant_id = %TENANT_ID%")) {
            sql = sql.replace(".tenant_id = %TENANT_ID%", ".tenant_id = '" + TenantIdStorage.getTenantId() + "'");
        }
        //log.info(sql);
        return sql;
    }
}
