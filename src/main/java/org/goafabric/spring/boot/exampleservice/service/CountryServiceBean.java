package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.logic.CountryLogicBean;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@RequestMapping(value = CountryService.RESOURCE,
        produces = "application/json")

@RestController
public class CountryServiceBean implements CountryService {
    @Autowired
    private CountryLogicBean countryLogicBean;


    @GetMapping("getById/{id}")
    public Country getById(@PathVariable("id") String id) {
        return countryLogicBean.getById(id);
    }

    @GetMapping("findAll")
    public List<Country> findAll() {
        return countryLogicBean.findAll();
    }

    @GetMapping("findByIsoCode")
    public Country findByIsoCode(@RequestParam("isoCode") String isoCode) {
        return countryLogicBean.findByIsoCode(isoCode);
    }

    @GetMapping("findByName")
    public Country findByName(@RequestParam("name") String name) {
        return countryLogicBean.findByName(name);
    }

    @PostMapping("save")
    public Country save(Country country) {
        return countryLogicBean.save(country);
    }

    @DeleteMapping("delete")
    public void delete(@RequestParam("id") String id) {
        countryLogicBean.delete(id);
    }

}
