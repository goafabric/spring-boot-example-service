package org.goafabric.spring.boot.exampleservice.crossfunctional;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class AuditBean {
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
        private Date createdAt;
        private String modifiedBy;
        private Date   modifiedAt;
        private String oldValue;
        private String newValue;
    }

    public void afterInsert(Object person) {
        logAudit(person, DbOperation.INSERT);
    }

    public void afterUpdate(Object person) {
        logAudit(person, DbOperation.UPDATE);
    }

    public void afterDelete(Object person) {
        logAudit(person, DbOperation.DELETE);
    }

    private void logAudit(final Object object, final DbOperation operation) {
        try {
            final String value =
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);

            final AuditEvent auditEvent;
            final String type = object.getClass().getSimpleName();
            if (operation == DbOperation.INSERT) {
                auditEvent = createInsertEvent(type, value);
            } else if (operation == DbOperation.UPDATE) {
                auditEvent = createUpdateEvent(type, "", value);
            } else if (operation == DbOperation.DELETE) {
                auditEvent = createDeleteEvent(type, value);
            } else {
                throw new IllegalStateException("Unsupported Operation: ");
            }

            log.info("New audit event :\n{}", auditEvent.toString());
        } catch (Exception e) {
            log.error("Error during audit:\n{}", e.getMessage(), e);
        }
    }

    private AuditEvent createInsertEvent(
            final String type, final String newValue) {
        return AuditEvent.builder()
                .operation(DbOperation.INSERT)
                .type(type)
                .createdBy(getUserName())
                .createdAt(LocalDateTime.now().toDate())
                .oldValue(null)
                .newValue(newValue)
                .build();
    }

    private AuditEvent createUpdateEvent(
            final String type, final String oldValue, final String newValue) {
        return AuditEvent.builder()
                .operation(DbOperation.UPDATE)
                .type(type)
                .modifiedBy(getUserName())
                .modifiedAt(LocalDateTime.now().toDate())
                .oldValue(null)
                .newValue(newValue)
                .build();
    }

    private AuditEvent createDeleteEvent(
            final String type, final String oldValue) {
        return AuditEvent.builder()
                .operation(DbOperation.DELETE)
                .type(type)
                .modifiedBy(getUserName())
                .modifiedAt(LocalDateTime.now().toDate())
                .oldValue(oldValue)
                .newValue(null)
                .build();
    }

    private String getUserName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication == null) ? "" : authentication.getName();
    }
}
