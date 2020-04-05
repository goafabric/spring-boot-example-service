package org.goafabric.spring.boot.exampleservice.service;

import io.swagger.annotations.ApiOperation;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;

import java.util.List;

public interface CountryService {
    String RESOURCE = "/countries";

    @ApiOperation("Retrieve the country by id")
    Country getById(String id);

    @ApiOperation("Retrieve all countrys")
    List<Country> findAll();

    @ApiOperation("Retrieve the country by isocode")
    Country findByIsoCode(String isoCode);

    @ApiOperation("Retrieve the country name")
    Country findByName(String name);

    @ApiOperation("Store the country")
    Country save(Country country);

    @ApiOperation("Delete the country")
    void delete(String id);

    @ApiOperation("Isalive that connects to another Service")
    Boolean isAlive();
}
