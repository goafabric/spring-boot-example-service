package org.goafabric.spring.boot.exampleservice.crossfunctional;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.util.Date;

/**
 * Created by andreas.mautsch on 31.01.2018.
 */

@Slf4j
public class AuditListener {
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
