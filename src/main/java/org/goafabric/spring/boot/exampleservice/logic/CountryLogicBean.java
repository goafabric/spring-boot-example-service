package org.goafabric.spring.boot.exampleservice.logic;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.exampleservice.persistence.repository.CountryRepository;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@Slf4j
@Component
public class CountryLogicBean {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return Arrays.asList(
                createStubCountry()
        );
    }

    public Country findCountryByIsoCode(@NonNull final String isoCode) {
        countryRepository.findByIsoCode(isoCode);
        return createStubCountry();
    }

    public Country findCountryByName(@NonNull final String name) {
        countryRepository.findByName(name);
        return createStubCountry();
    }


    public void save(@NonNull final Country country) {

    }

    public void delete(@NonNull final String id) {

    }

    private Country createStubCountry() {
        return Country.builder()
                .id("1").isoCode("de").name("Germany")
                .build();
    }

}

