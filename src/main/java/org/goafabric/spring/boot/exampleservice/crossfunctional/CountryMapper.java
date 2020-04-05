package org.goafabric.spring.boot.exampleservice.crossfunctional;

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
    CountryBo toBo(Country country);

    @Mapping(source = "information", target = "description")
    Country   toDto(CountryBo country);

    List<CountryBo> toBo(List<Country> countries);
    List<Country> toDtos(List<CountryBo> countries);
}
