package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.logic.CountryLogic;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CountryLogicIT {
    @Autowired
    private CountryLogic countryLogic;

    @Test
    public void testGetAllCountries() {
        final List<Country> countries = countryLogic.findAll();
        assertThat(countries).isNotNull().isNotEmpty();
    }

    @Test
    public void testFindCountryByIsoCode() {
        final Country country = countryLogic.findByIsoCode("de");
        assertThat(country).isNotNull();
        assertThat(country.getIsoCode()).isEqualTo("de");
        assertThat(country.getName()).isEqualTo("Germany");
    }

    @Test
    public void testFindCountryByName() {
        final Country country = countryLogic.findByName("Germany");
        assertThat(country.getIsoCode()).isEqualTo("de");
        assertThat(country.getName()).isEqualTo("Germany");
    }

    @Test
    public void testDelete() {
        countryLogic.save(createStubCountry());

        final Country country = countryLogic.findByIsoCode("pi");
        countryLogic.delete(country.getId());
    }

    @Test
    public void testFindCountryByIsoCodeNull() {
        assertThatThrownBy(() ->
            countryLogic.findByIsoCode(null)).isInstanceOf(IllegalArgumentException.class);
    }

    private Country createStubCountry() {
        final Country country = Country.builder()
                .isoCode("pi")
                .name("Phantasy Island")
                .description("The Island where magic happens")
                .build();
        country.setName("Phantasy Star");
        return country;
    }


}