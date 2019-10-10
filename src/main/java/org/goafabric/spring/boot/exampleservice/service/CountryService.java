package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.service.dto.Country;

import java.util.List;

public interface CountryService {
    String RESOURCE = "/countries";

    Country getById(String id);

    List<Country> findAll();

    Country findByIsoCode(String isoCode);

    Country findByName(String name);

    Country save(Country country);

    void delete(String id);
}
