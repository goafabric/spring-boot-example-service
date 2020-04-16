package org.goafabric.spring.boot.exampleservice.logic;

import org.goafabric.spring.boot.exampleservice.persistence.domain.CountryBo;
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
    CountryBo map(Country country);

    @Mapping(source = "information", target = "description")
    Country map(CountryBo country);

    List<Country> map(List<CountryBo> countries);
}
