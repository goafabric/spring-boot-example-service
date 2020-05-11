/*
package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.exampleservice.configuration.EncryptionConfiguration;
import org.hibernate.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;

@Slf4j
@Configuration
public class TenantTransactionManagerConfiguration {
    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager
                = new TenantTransactionManager();
        //transactionManager.setEntityManagerFactory(
        //      entityManagerFactoryBean().getObject() );
        return transactionManager;
    }

    private static class TenantTransactionManager extends org.springframework.orm.jpa.JpaTransactionManager {

        protected EntityManager createEntityManagerForTransaction() {
            final EntityManager entityManager = super.createEntityManagerForTransaction();
            log.info("inside tenant");
            //EnableFilterAspect.addTenantFilter(entityManager);
            final Session session = entityManager.unwrap(Session.class);
            session.enableFilter(TenantAware.TENANT_FILTER_NAME).setParameter(TenantAware.TENANT_ID_PROPERTY_NAME, "10");

            return entityManager;
        }
    }
}
*/