package org.goafabric.spring.boot.exampleservice.persistence.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */
@Entity
@Table(name="country")
@Data
public class CountryBO {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String isoCode;
    private String name;

    @Version //optimistic locking
    private Long version;
}
