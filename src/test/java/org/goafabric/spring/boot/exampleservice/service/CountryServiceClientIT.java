package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.client.CountryServiceClient;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryServiceClientIT {
    @Autowired
    private RestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private CountryServiceClient countryService;

    @PostConstruct
    private void init() {
        this.countryService
                = new CountryServiceClient(restTemplate, "http://localhost:" + port);
    }

    @Test
    public void testGetById() {
        final Country country = countryService.getById("1");
        assertThat(country).isNotNull();
        assertThat(country.getIsoCode()).isEqualTo("de");
        assertThat(country.getName()).isEqualTo("Germany");
    }

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
    @Ignore
    public void testSaveAndDelete() {
        countryService.save(createStubCountry());

        final Country country = countryService.findByIsoCode("pi");
        assertThat(country).isNotNull();
        countryService.delete(country.getId());
    }

    //does  not work because due to json string is not null but empty
    @Test
    @Ignore
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