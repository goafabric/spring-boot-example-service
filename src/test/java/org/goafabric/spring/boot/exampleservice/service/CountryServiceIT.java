package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.logic.CountryLogicBean;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Created by andreas.mautsch on 11.06.2018.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class CountryServiceIT {
    /*
    @Autowired
    private CountryService countryService;
    */

    @Autowired
    private CountryLogicBean countryService;

    @Test
    public void testGetAllCountries() {
        assertThat(countryService.getAllCountries()).isNotNull();
    }

    @Test
    public void testFindCountryByIsoCode() {
        final Country country = countryService.findCountryByIsoCode("de");
        assertThat(country).isNotNull();
        assertThat(country.getIsoCode()).isEqualTo("de");
        assertThat(country.getName()).isEqualTo("Germany");
    }

    @Test
    public void testFindCountryByName() {
        final Country country = countryService.findCountryByName("Germany");
        assertThat(country.getIsoCode()).isEqualTo("de");
        assertThat(country.getName()).isEqualTo("Germany");
    }

    @Test
    public void testSave() {
        countryService.save(createStubCountry());
    }

    @Test
    public void testDelete() {
        countryService.delete("1");
    }

    private Country createStubCountry() {
        return Country.builder()
                .id("1").isoCode("de").name("Germany")
                .build();
    }


}