package org.goafabric.spring.boot.exampleservice.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {
    private String id;

    @NotNull
    @Size(min = 2, max = 2)
    private String isoCode;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
}
