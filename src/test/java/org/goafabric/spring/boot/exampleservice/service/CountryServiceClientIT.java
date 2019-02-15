package org.goafabric.spring.boot.exampleservice.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.goafabric.spring.boot.exampleservice.client.CountryServiceClient;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@SpringBootTest
@RunWith(SpringRunner.class)
public class CountryServiceClientIT {
    @Autowired
    private CountryServiceClient countryService;
    //@LocalServerPort is a meta-annotation for @Value("${local.server.port}")

    @Test
    public void testGetById() {
        final Country country = countryService.getById("1");
        AssertionsForClassTypes.assertThat(country).isNotNull();
        AssertionsForClassTypes.assertThat(country.getIsoCode()).isEqualTo("de");
        AssertionsForClassTypes.assertThat(country.getName()).isEqualTo("Germany");
    }

    @Test
    public void testGetAllCountries() {
        final List<Country> countries = countryService.findAll();
        assertThat(countries).isNotNull().isNotEmpty();
    }

    @Test
    public void testFindCountryByIsoCode() {
        final Country country = countryService.findByIsoCode("de");
        AssertionsForClassTypes.assertThat(country).isNotNull();
        AssertionsForClassTypes.assertThat(country.getIsoCode()).isEqualTo("de");
        AssertionsForClassTypes.assertThat(country.getName()).isEqualTo("Germany");
    }

    @Test
    public void testFindCountryByName() {
        final Country country = countryService.findByName("Germany");
        AssertionsForClassTypes.assertThat(country.getIsoCode()).isEqualTo("de");
        AssertionsForClassTypes.assertThat(country.getName()).isEqualTo("Germany");
    }

    @Test
    public void testSaveAndDelete() {
        countryService.save(createStubCountry());

        final Country country = countryService.findByIsoCode("pi");
        assertThat(country).isNotNull();
        countryService.delete(country.getId());
    }

    //does  not work because due to json string is not null but empty
    @Test
    public void testFindCountryByIsoCodeNull() {
        assertThatThrownBy(() ->
                countryService.findByIsoCode(null)).isInstanceOf(IllegalArgumentException.class);
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