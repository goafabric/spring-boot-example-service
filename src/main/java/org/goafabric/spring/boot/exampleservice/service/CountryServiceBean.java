package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.logic.CountryLogicBean;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(path = "findAll", method = RequestMethod.GET)
    public List<Country> findAll() {
        return countryLogicBean.findAll();
    }

    @RequestMapping(path = "findByIsoCode", method = RequestMethod.GET)
    public Country findByIsoCode(@RequestParam("isoCode") String isoCode) {
        return countryLogicBean.findByIsoCode(isoCode);
    }

    @RequestMapping(path = "findByName", method = RequestMethod.GET)
    public Country findByName(@RequestParam("name") String name) {
        return countryLogicBean.findByName(name);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(Country country) {
        countryLogicBean.save(country);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@RequestParam("id") String id) {
        countryLogicBean.delete(id);
    }

}
