package org.goafabric.spring.boot.exampleservice.service.dto;

import lombok.Builder;
import lombok.Value;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */

@Value
@Builder
public class Country {
    private String id;
    private String isoCode;
    private String name;
}
