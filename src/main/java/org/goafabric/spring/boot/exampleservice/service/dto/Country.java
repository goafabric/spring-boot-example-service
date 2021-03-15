package org.goafabric.spring.boot.exampleservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Size(max = 255)
    private String secret;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //private LocalDateTime date;

    private Long version;
}
