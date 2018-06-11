package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@RequestMapping(CountryService.RESOURCE)
public interface CountryService {
    String RESOURCE = "/countries";

    @RequestMapping(method = RequestMethod.GET)
    List<Country> getAllCountries();
}
