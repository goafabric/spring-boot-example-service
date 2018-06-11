package org.goafabric.spring.boot.exampleservice.logic;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@Slf4j
@Component
public class CountryLogicBean {
    public List<Country> getAllCountries() {
        return Arrays.asList(
                createStubCountry()
        );
    }

    public Country findCountyByIsoCode(final String isoCode) {
        return createStubCountry();
    }

    public Country findCountryByName(final String name) {
        return createStubCountry();
    }


    public void save(final Country country) {

    }

    public void delete(final String id) {

    }

    private Country createStubCountry() {
        return Country.builder()
                .id("1").isoCode("de").name("Germany")
                .build();
    }

}

