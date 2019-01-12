package org.goafabric.spring.boot.exampleservice.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.joda.time.LocalDateTime;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
}
