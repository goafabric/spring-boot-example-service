package org.goafabric.spring.boot.exampleservice.persistence.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */
@Entity
@Table(name="country") //, schema = "countries")
@Data
public class CountryBo {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "isocode")
    private String isoCode;

    private String name;
    private String information;

    @Type(type="encryptedString")
    private String secret;

    @Version //optimistic locking
    private Long version;
}
