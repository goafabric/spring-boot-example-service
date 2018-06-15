package org.goafabric.spring.boot.exampleservice.service.dto;

import lombok.*;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {
    private String id;
    private String isoCode;
    private String name;
    private String description;
}
