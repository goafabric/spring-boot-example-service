package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@RequestMapping(CountryService.RESOURCE)
public interface CountryService {
    String RESOURCE = "/countries";

    @RequestMapping(path = "findAll", method = RequestMethod.GET)
    List<Country> findAll();

    @RequestMapping(path = "findByIsoCode", method = RequestMethod.GET)
    Country findByIsoCode(@RequestParam("isoCode") String isoCode);

    @RequestMapping(path = "findByName", method = RequestMethod.GET)
    Country findByName(@RequestParam("name") String name);

    @RequestMapping(method = RequestMethod.POST)
    void save(Country country);

    @RequestMapping(method = RequestMethod.DELETE)
    void delete(@RequestParam("id") String id);
}
