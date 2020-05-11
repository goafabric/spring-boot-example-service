/*
package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;


@Aspect
@Component
@Slf4j
public class TenantAspect {
    @Autowired
    private EntityManager entityManager;

    @Around("execution(* org.springframework.data.repository.Repository+.*(..))")
    public Object activateTenantFilter(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("before repository");
        if (joinPoint.getArgs()[0].toString().contains("property")) {
            return joinPoint.proceed();
        }

        if (joinPoint.getArgs()[0].toString().contains("database")) {
            return joinPoint.proceed();
        }

        Object object = joinPoint.proceed();
        final Session session = entityManager.unwrap(Session.class);
        session.enableFilter(TenantAware.TENANT_FILTER_NAME).setParameter(TenantAware.TENANT_ID_PROPERTY_NAME, "10");
        return object;
    }
}
*/