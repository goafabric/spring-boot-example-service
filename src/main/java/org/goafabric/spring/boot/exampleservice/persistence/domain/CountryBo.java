package org.goafabric.spring.boot.exampleservice.persistence.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.goafabric.spring.boot.exampleservice.persistence.multitenancy.TenantAware;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */
@Entity
@Table(name="country") //, schema = "countries")
@Data
@EqualsAndHashCode(callSuper = true)
@Where(clause = TenantAware.TENANT_FILTER)
public class CountryBo extends TenantAware {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String isoCode;

    private String name;
    private String information;

    @Type(type="encryptedSearchableString")
    private String secret;

    @Version //optimistic locking
    private Long version;
}
