package org.goafabric.spring.boot.exampleservice.crossfunctional;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.exampleservice.logic.CountryLogic;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.util.Date;

@Component
@Slf4j
public class AuditBean {
    @Autowired
    private CountryLogic countryLogic;

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
        private String value;
    }

    @PostPersist
    public void afterInsert(Object person) {
        logAudit(person, DbOperation.INSERT);
    }

    @PostUpdate
    public void afterUpdate(Object person) {
        logAudit(person, DbOperation.UPDATE);
    }

    @PostRemove
    public void afterDelete(Object person) {
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
            final Object object, final DbOperation operation, final  String value) {
        return AuditEvent.builder()
                .operation(operation)
                .type(object.getClass().getSimpleName())
                .modifiedBy(getUserName())
                .modifiedAt(LocalDateTime.now().toDate())
                .value(value)
                .build();
    }

    private AuditEvent createInsertEvent(
            final Object object, final DbOperation operation, final String value) {
        return AuditEvent.builder()
                .operation(operation)
                .type(object.getClass().getSimpleName())
                .createdBy(getUserName())
                .createdAt(LocalDateTime.now().toDate())
                .value(value)
                .build();
    }

    private String getUserName() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication == null) ? "" : authentication.getName();
    }
}
