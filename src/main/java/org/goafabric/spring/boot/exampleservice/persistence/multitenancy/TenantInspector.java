package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.exampleservice.crossfunctional.TenantStorage;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TenantInspector implements StatementInspector {
    //inspect sqls from entities annotated with @WHERE(".tenant_id = %TENANT_ID%")
    //we intentionally omit the ' inside the @WHERE, because if the replacement will not take place, the sql preparation will crash
    @Override
    public String inspect(String sql) {
        if (sql.contains(TenantAware.TENANT_FILTER)) {
            sql = sql.replace(TenantAware.TENANT_FILTER, "tenant_id = '" + TenantStorage.getTenantId() + "'");
        }
        //log.info(sql);
        return sql;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties -> hibernateProperties.put("hibernate.session_factory.statement_inspector",
                TenantInspector.class.getName());
    }
}
