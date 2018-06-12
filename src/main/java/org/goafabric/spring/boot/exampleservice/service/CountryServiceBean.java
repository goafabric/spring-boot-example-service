package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.logic.CountryLogicBean;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@RequestMapping(CountryServiceBean.RESOURCE)
@RestController
public class CountryServiceBean{
    @Autowired
    private CountryLogicBean countryLogicBean;

    public static final String RESOURCE = "/countries";

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

    @PostMapping
    public void save(Country country) {
        countryLogicBean.save(country);
    }

    @DeleteMapping
    public void delete(@RequestParam("id") String id) {
        countryLogicBean.delete(id);
    }

}
