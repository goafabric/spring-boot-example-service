package org.goafabric.spring.boot.exampleservice.crossfunctional;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.exampleservice.logic.CountryLogic;
import org.joda.time.LocalDateTime;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.util.Date;

/**
 * Created by andreas.mautsch on 31.01.2018.
 */

@Slf4j
public class AuditListener {
    @Service
    private static class BeanUtil implements ApplicationContextAware {
        private static ApplicationContext context;
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            context = applicationContext;
        }
        public static <T> T getBean(Class<T> beanClass) {
            return context.getBean(beanClass);
        }
    }

    private enum DbOperation {
        INSERT,
        UPDATE,
        DELETE
    }

    @Data
    @Builder
    private static class AuditEvent {
        private String type;
        private DbOperation operation;
        private String createdBy;
        private Date   createdAt;
        private String modifiedBy;
        private Date   modifiedAt;
        private String value;
    }

    @PostPersist
    public void postPersist(Object person) {
        logAudit(person, DbOperation.INSERT);
    }

    @PostUpdate
    public void postUpdate(Object person) {
        logAudit(person, DbOperation.UPDATE);
    }

    @PostRemove
    public void postRemove(Object person) {
        logAudit(person, DbOperation.DELETE);
    }

    private void logAudit(final Object object, final DbOperation operation) {
        try {
            final String value =
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);

            final AuditEvent auditEvent;
            if (operation == DbOperation.INSERT) {
                auditEvent = createInsertEvent(object, operation, value);
            } else {
                auditEvent = createUpdateDeleteEvent(object, operation, value);
            }

            CountryLogic logic = BeanUtil.getBean(CountryLogic.class);
            log.info("New audit event : {}", auditEvent.toString());
        } catch (Exception e) {
            log.error("Error during audit: {}", e.getMessage(), e);
        }
    }

    private AuditEvent createUpdateDeleteEvent(
            final Object object, final DbOperation operation,final  String value) {
        return AuditEvent.builder()
                .operation(operation)
                .type(object.getClass().getSimpleName())
                .modifiedBy(getUserLogin())
                .modifiedAt(LocalDateTime.now().toDate())
                .value(value)
                .build();
    }

    private AuditEvent createInsertEvent(
            final Object object, final DbOperation operation, final String value) {
        return AuditEvent.builder()
                .operation(operation)
                .type(object.getClass().getSimpleName())
                .createdBy(getUserLogin())
                .createdAt(LocalDateTime.now().toDate())
                .value(value)
                .build();
    }

    private String getUserLogin()  {
        return "John Doe"; //SecurityExecutionContext.currentContext().getUserLoginName()
    }
}
