package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.logic.CountryLogicBean;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@RequestMapping(value = CountryService.RESOURCE,
        produces = MediaType.APPLICATION_JSON_VALUE)

@RestController
//@PreAuthorize("hasRole('STANDARD_ROLE')")
public class CountryServiceBean implements CountryService {
    @Autowired
    private CountryLogicBean countryLogicBean;

    @GetMapping("getById")
    public Country getById(@RequestParam String id) {
        return countryLogicBean.getById(id);
    }

    @GetMapping("findAll")
    public List<Country> findAll() {
        return countryLogicBean.findAll();
    }

    @GetMapping("findByIsoCode")
    public Country findByIsoCode(@RequestParam String isoCode) {
        return countryLogicBean.findByIsoCode(isoCode);
    }

    @GetMapping("findByName")
    public Country findByName(@RequestParam String name) {
        return countryLogicBean.findByName(name);
    }

    @PostMapping(value = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Country save(@RequestBody Country country) {
        return countryLogicBean.save(country);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam String id) {
        countryLogicBean.delete(id);
    }

}
