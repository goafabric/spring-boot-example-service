package org.goafabric.spring.boot.exampleservice.persistence.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.exampleservice.crossfunctional.TenantIdInterceptor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
//also see https://www.baeldung.com/database-auditing-jpa
/**
 * A class that audits all registered entities with @EntityListeners
 * And writes the Audit Entries to the database
 */
public class AuditBean {
    private enum DbOperation {
        CREATE,
        READ,
        UPDATE,
        DELETE
    }

    @Data
    @Builder
    static class AuditEvent {
        private String id;
        private String tenantId;
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

    interface AuditInserter {
        void insertAudit(AuditEvent auditEvent, Object object);
    }

    @Autowired
    private AuditInserter auditInserter;

    @Autowired
    private StringEncryptor databaseEncryptor;

    public void afterRead(Object object, String id) {
        insertAudit(DbOperation.READ, id, object, object);
    }

    public void afterCreate(Object object, String id) {
        insertAudit(DbOperation.CREATE, id, null, object);
    }

    public void afterUpdate(Object object, String id, Object oldObject) {
        insertAudit(DbOperation.UPDATE, id, oldObject, object);
    }

    public void afterDelete(Object object, String id) {
        insertAudit(DbOperation.DELETE, id, object, null);
    }

    private void insertAudit(final DbOperation operation, String referenceId, final Object oldObject, final Object newObject) {
        try {
            final AuditEvent auditEvent =
                createAuditEvent(operation, referenceId, oldObject, newObject);
            auditInserter.insertAudit(auditEvent, oldObject != null ? oldObject : newObject);
            logAudit(auditEvent);
        } catch (Exception e) {
            log.error("Error during audit:\n{}", e.getMessage(), e);
        }
    }

    private void logAudit(AuditEvent auditEvent) {
        if (log.isDebugEnabled()) {
            auditEvent.setOldValue(databaseEncryptor.decrypt(auditEvent.getOldValue()));
            auditEvent.setNewValue(databaseEncryptor.decrypt(auditEvent.getNewValue()));
            log.debug("New audit event :\n{}", auditEvent.toString());
        }
    }

    private AuditEvent createAuditEvent(
            DbOperation dbOperation, String referenceId, final Object oldObject, final Object newObject) throws JsonProcessingException {
        final Date date = new Date(System.currentTimeMillis());
        return AuditEvent.builder()
                .id(UUID.randomUUID().toString())
                .referenceId(referenceId)
                .tenantId(TenantIdInterceptor.getTenantId())
                .operation(dbOperation)
                .createdBy(dbOperation == DbOperation.CREATE ? getUserName() : null)
                .createdAt(dbOperation == DbOperation.CREATE ? date : null)
                .modifiedBy((dbOperation == DbOperation.UPDATE || dbOperation == DbOperation.DELETE) ? getUserName() : null)
                .modifiedAt((dbOperation == DbOperation.UPDATE || dbOperation == DbOperation.DELETE) ? date : null)
                .oldValue(oldObject == null ? null : databaseEncryptor.encrypt(getJsonValue(oldObject)))
                .newValue(newObject == null ? null : databaseEncryptor.encrypt(getJsonValue(newObject)))
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
