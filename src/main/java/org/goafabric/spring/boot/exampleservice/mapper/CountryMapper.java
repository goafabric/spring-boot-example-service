package org.goafabric.spring.boot.exampleservice.mapper;

import org.goafabric.spring.boot.exampleservice.persistence.domain.CountryBO;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */

@Mapper(componentModel = "spring")
public interface CountryMapper {
    @Mapping(source = "description", target = "information")
    CountryBO toBO(Country country);

    @Mapping(source = "information", target = "description")
    Country   toDTO(CountryBO country);

    List<CountryBO> toBO(List<Country> countries);
    List<Country> toDTOs(List<CountryBO> countries);
}
