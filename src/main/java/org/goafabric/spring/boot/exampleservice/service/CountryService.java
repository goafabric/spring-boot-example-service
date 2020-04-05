package org.goafabric.spring.boot.exampleservice.service;

import io.swagger.annotations.ApiOperation;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface CountryService {
    String RESOURCE = "/countries";

    @ApiOperation("Retrieve the country by id")
    @GetMapping("getById")
    Country getById(@RequestParam String id);

    @ApiOperation("Retrieve all countrys")
    @GetMapping("findAll")
    List<Country> findAll();

    @ApiOperation("Retrieve the country by isocode")
    @GetMapping("findByIsoCode")
    Country findByIsoCode(@RequestParam String isoCode);

    @ApiOperation("Retrieve the country name")
    @GetMapping("findByName")
    Country findByName(@RequestParam String name);

    @ApiOperation("Store the country")
    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    Country save(@RequestBody @Valid Country country);

    @ApiOperation("Delete the country")
    @DeleteMapping("delete")
    void delete(@RequestParam String id);

    @ApiOperation("Isalive that connects to another Service")
    @GetMapping("isAlive")
    Boolean isAlive();
}
