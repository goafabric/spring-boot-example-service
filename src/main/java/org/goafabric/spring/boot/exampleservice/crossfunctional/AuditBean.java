package org.goafabric.spring.boot.exampleservice.crossfunctional;

import com.fasterxml.jackson.core.JsonProcessingException;
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
//TODO: should also be stored in database
//There are also standard frameworks (tightly coupled with JPA): https://www.baeldung.com/database-auditing-jpa
public class AuditBean {
    private enum DbOperation {
        INSERT,
        UPDATE,
        DELETE
    }

    @Data
    @Builder
    private static class AuditEvent {
        private String id;
        private String referenceId;
        private String type;
        private DbOperation operation;
        private String createdBy;
        private Date createdAt;
        private String modifiedBy;
        private Date   modifiedAt;
        private String oldValue;
        private String newValue;
    }

    public void afterInsert(String id, Object object) {
        logAudit(id, object, DbOperation.INSERT);
    }

    public void afterUpdate(String id, Object object) {
        logAudit(id, object, DbOperation.UPDATE);
    }

    public void afterDelete(String id, Object object) {
        logAudit(id, object, DbOperation.DELETE);
    }

    private void logAudit(String referenceId, final Object object, final DbOperation operation) {
        try {
            final String value = getJsonValue(object);

            final AuditEvent auditEvent;
            if (operation == DbOperation.INSERT) {
                auditEvent = createInsertEvent(referenceId, object, value);
            } else if (operation == DbOperation.UPDATE) {
                auditEvent = createUpdateEvent(referenceId, object, "", value); //TODO: we nee the oldvalue here
            } else if (operation == DbOperation.DELETE) {
                auditEvent = createDeleteEvent(referenceId, object, value);
            } else {
                throw new IllegalStateException("Unsupported Operation: ");
            }

            log.info("New audit event :\n{}", auditEvent.toString());
        } catch (Exception e) {
            log.error("Error during audit:\n{}", e.getMessage(), e);
        }
    }

    private AuditEvent createInsertEvent(
            String referenceId, final Object object, final String newValue) {
        return AuditEvent.builder()
                .referenceId(referenceId)
                .operation(DbOperation.INSERT)
                .type(object.getClass().getSimpleName())
                .createdBy(getUserName())
                .createdAt(LocalDateTime.now().toDate())
                .oldValue(null)
                .newValue(newValue)
                .build();
    }

    private AuditEvent createUpdateEvent(
            String referenceId, final Object object, final String oldValue, final String newValue) {
        return AuditEvent.builder()
                .referenceId(referenceId)
                .operation(DbOperation.UPDATE)
                .type(object.getClass().getSimpleName())
                .modifiedBy(getUserName())
                .modifiedAt(LocalDateTime.now().toDate())
                .oldValue(null)
                .newValue(newValue)
                .build();
    }

    private AuditEvent createDeleteEvent(
            String referenceId, final Object object, final String oldValue) {
        return AuditEvent.builder()
                .referenceId(referenceId)
                .operation(DbOperation.DELETE)
                .type(object.getClass().getSimpleName())
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

    private String getJsonValue(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}
