package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.logic.CountryLogic;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@RequestMapping(value = CountryService.RESOURCE,
        produces = MediaType.APPLICATION_JSON_VALUE)

@RestController
@PreAuthorize("hasRole('STANDARD_ROLE')")
public class CountryServiceBean implements CountryService {
    @Autowired
    private CountryLogic countryLogic;

    @GetMapping("getById")
    public Country getById(@RequestParam String id) {
        return countryLogic.getById(id);
    }

    @GetMapping("findAll")
    public List<Country> findAll() {
        return countryLogic.findAll();
    }

    @GetMapping("findByIsoCode")
    public Country findByIsoCode(@RequestParam String isoCode) {
        return countryLogic.findByIsoCode(isoCode);
    }

    @GetMapping("findByName")
    public Country findByName(@RequestParam String name) {
        return countryLogic.findByName(name);
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Country save(@RequestBody Country country) {
        return countryLogic.save(country);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam String id) {
        countryLogic.delete(id);
    }

    @GetMapping("isAlive")
    public Boolean isAlive() {
        return countryLogic.isAlive();
    }

}
