package org.goafabric.spring.boot.exampleservice.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.goafabric.spring.boot.exampleservice.client.CountryServiceClient;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CountryServiceClientIT {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private String serviceBaseUrl;

    @LocalServerPort
    private int port;

    private CountryServiceClient countryService;

    @PostConstruct
    private void init() {
        this.countryService
                = new CountryServiceClient(restTemplate, serviceBaseUrl + ":" + port);
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
    public void testFindCountryBySecret() {
        final String id = countryService.save(createStubCountry()).getId();
        Country country = countryService.getById(id);

        assertThat(country.getIsoCode()).isEqualTo("pi");
        assertThat(country.getSecret()).isEqualTo("Top Secret Information");

        Country foundCountry = countryService.findBySecret("Top Secret Information");
        assertThat(foundCountry).isNotNull();
    }

    @Test
    public void testSaveAndDelete() {
        countryService.save(createStubCountry());
        final Country country = countryService.findByIsoCode("pi");
        assertThat(country.getSecret()).isEqualTo("Top Secret Information");

        assertThat(country).isNotNull();
        final String id = country.getId();
        countryService.delete(id);
    }

    @Test
    public void testUpdate() {
        final Country country  = countryService.save(createStubCountry());

        country.setIsoCode("hw");
        country.setName("Hawaii");

        AssertionsForClassTypes.assertThat(countryService.save(country).getId())
                .isEqualTo(country.getId());

        final Country updatedCountry = countryService.getById(country.getId());
        assertThat(updatedCountry).isNotNull();
        assertThat(updatedCountry.getName()).isEqualTo("Hawaii");
    }

    @Test
    public void testCacheEvict() {
        countryService.save(createStubCountry());
        final Country country = countryService.findByIsoCode("pi");
        final String id = country.getId();
        countryService.delete(country.getId());

        countryService.save(createStubCountry());
        final Country country2 = countryService.findByIsoCode("pi");
        countryService.delete(country2.getId());
        assertThat(country2.getId()).isNotEqualTo(id);
    }

    @Test
    public void testNegativeSaveValidationFailed() {
        try {
            final Country country = createStubCountry();
            country.setName(null);
            countryService.save(country);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
        }

        try {
            final Country country = createStubCountry();
            country.setIsoCode("too long");
            countryService.save(country);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
        }

    }

    //does  not work because due to json string is not null but empty
    @Test
    @Ignore
    public void testFindCountryByIsoCodeNull() {
        assertThatThrownBy(() ->
                countryService.findByIsoCode(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testNotFound() {
        try {
            countryService.getById("xxzz");
            fail("should net get here");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(e.getResponseBodyAsString()).isNotNull();
        }
    }

    @Test
    public void testIsAlive() {
        try {
            countryService.isAlive();
            fail("should net get here");
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(e.getResponseBodyAsString()).isNotNull();
        }
    }

    private Country createStubCountry() {
        final Country country = Country.builder()
                .isoCode("pi")
                .name("Phantasy Island")
                .description("The Island where magic happens")
                .secret("Top Secret Information")
                .build();
        country.setName("Phantasy Star");
        return country;
    }


}