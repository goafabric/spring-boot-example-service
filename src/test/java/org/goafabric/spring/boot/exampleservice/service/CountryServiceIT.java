package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */

@SpringBootTest()
@RunWith(SpringRunner.class)
public class CountryServiceIT {
    @Autowired
    private CountryServiceBean countryService;

    @Test
    public void testGetAllCountries() {
        final List<Country> countries = countryService.findAll();
        assertThat(countries).isNotNull().isNotEmpty();
    }

    @Test
    public void testFindCountryByIsoCode() {
        final Country country = countryService.findByIsoCode("de");
        assertThat(country).isNotNull();
        assertThat(country.getIsoCode()).isEqualTo("de");
        assertThat(country.getName()).isEqualTo("Germany");
    }

    @Test
    public void testFindCountryByName() {
        final Country country = countryService.findByName("Germany");
        assertThat(country.getIsoCode()).isEqualTo("de");
        assertThat(country.getName()).isEqualTo("Germany");
    }

    @Test
    public void testDelete() {
        countryService.save(createStubCountry());

        final Country country = countryService.findByIsoCode("pi");
        countryService.delete(country.getId());
    }

    private Country createStubCountry() {
        return Country.builder()
                .isoCode("pi")
                .name("Phantasy Island")
                .description("The Island where magic happens")
                .build();
    }


}