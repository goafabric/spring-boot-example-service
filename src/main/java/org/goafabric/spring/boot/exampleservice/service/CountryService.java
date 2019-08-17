package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = CountryService.RESOURCE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface CountryService {
    String RESOURCE = "/countries";

    @GetMapping("getById")
    Country getById(@RequestParam("id") String id);

    @GetMapping("findAll")
    List<Country> findAll();

    @GetMapping("findByIsoCode")
    Country findByIsoCode(@RequestParam("isoCode") String isoCode);

    @GetMapping("findByName")
    Country findByName(@RequestParam("name") String name);

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    Country save(@RequestBody Country country);

    @DeleteMapping("delete")
    void delete(@RequestParam String id);
}
