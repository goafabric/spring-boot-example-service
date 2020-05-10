package org.goafabric.spring.boot.exampleservice.persistence.multitenancy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TenantAspect {
    @Around("execution(* org.springframework.data.repository.CrudRepository.save(..))")
    public Object onSave(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object object = joinPoint.getArgs()[0];
        if (object instanceof TenantAware) {
            ((TenantAware) object).setTenantId(TenantIdStorage.getTenantId());
        }
        return joinPoint.proceed();
    }

    /*
    @Around("execution(* org.springframework.data.repository.CrudRepository.delete(..))")
    public Object onDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object object = joinPoint.getArgs()[0];
        if (object instanceof TenantAware) {
            ((TenantAware) object).setTenantId(TenantIdStorage.getTenantId());
        }
        return joinPoint.proceed();
    }
    */
}
