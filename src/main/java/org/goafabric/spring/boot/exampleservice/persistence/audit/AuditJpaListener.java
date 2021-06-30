package org.goafabric.spring.boot.exampleservice.persistence.audit;

import lombok.NonNull;
import org.goafabric.spring.boot.exampleservice.persistence.multitenancy.TenantAware;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.sql.DataSource;

/**
 * Specific Listener for JPA for Auditing
 *
 */

public class AuditJpaListener implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @PostLoad
    public void afterRead(Object object) {
        context.getBean(AuditBean.class).afterRead(object, getId(object));
    }

    @PostPersist
    public void afterCreate(Object object)  {
        context.getBean(AuditBean.class).afterCreate(object, getId(object));
    }

    @PostUpdate
    public void afterUpdate(Object object) {
        context.getBean(AuditBean.class).afterUpdate(object, getId(object),
                context.getBean(AuditJpaUpdater.class).findOldObject(object.getClass(), getId(object)));
    }

    @PostRemove
    public void afterDelete(Object object) {
        context.getBean(AuditBean.class).afterDelete(object, getId(object));
    }

    private String getId(@NonNull Object object) {
        return ((TenantAware) object).getId();
    }

    @Component
    static class AuditJpaUpdater {
        @Autowired private EntityManager entityManager;

        @Transactional(propagation = Propagation.REQUIRES_NEW) //new transaction helps us to retrieve the old value still inside the db
        public <T> T findOldObject(Class<T> clazz, String id) {
            return entityManager.find(clazz, id);
        }
    }

    @Component
    static class AuditJpaInserter implements AuditBean.AuditInserter {
        @Autowired
        private DataSource dataSource;

        public void insertAudit(AuditBean.AuditEvent auditEvent, Object object) { //we cannot use jpa because of the dynamic table name
            new SimpleJdbcInsert(dataSource).withTableName(getTableName(object) + "_audit")
                .execute(new BeanPropertySqlParameterSource(auditEvent));
        }

        private String getTableName(@NonNull Object object) {
            return object.getClass().getSimpleName().replaceAll("Bo", "").toLowerCase();
        }
    }
}
