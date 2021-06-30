package org.goafabric.spring.boot.exampleservice.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.goafabric.spring.boot.exampleservice.client.CountryServiceClient;
import org.goafabric.spring.boot.exampleservice.client.TenantIdClientStorage;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TenantIT {
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
        TenantIdClientStorage.setTenantId(null);
    }

    @Test
    public void testTenantNegative() {
        TenantIdClientStorage.setTenantId("20");
        try {
            assertThat(countryService.findAll()).hasSize(0);

            AssertionsForClassTypes.assertThat(countryService.findByIsoCode("de")).isNull();;
            AssertionsForClassTypes.assertThat(countryService.findByName("Germany")).isNull();;
        } finally {
            TenantIdClientStorage.setTenantId("0");
        }
    }

    @Test
    public void testTenantPositive() {
        TenantIdClientStorage.setTenantId("20");
        try {
            Country country = Country.builder().isoCode("nz")
                    .name("New Zealand 20")
                    .description("Land of the Daredevils")
                    .secret("You should not see this")
                    .build();

            final String id = countryService.save(country).getId();

            assertThat(countryService.findAll()).hasSize(1);
            AssertionsForClassTypes.assertThat(countryService.count()).isEqualTo(1);

            AssertionsForClassTypes.assertThat(countryService.getById(id).getId()).isEqualTo(id);
            AssertionsForClassTypes.assertThat(countryService.findByIsoCode("nz").getIsoCode()).isEqualTo("nz");
            AssertionsForClassTypes.assertThat(countryService.findByName("New Zealand 20").getName()).isEqualTo("New Zealand 20");

            countryService.delete(id);
            assertThatThrownBy(() -> countryService.getById(id))
                    .isInstanceOf(HttpClientErrorException.class)
                    .hasMessageContaining("No value present");
        } finally {
            TenantIdClientStorage.setTenantId("0");
        }
    }

    @Test
    public void testTenantCache() {
        Country country = Country.builder().isoCode("nz")
                .name("New Zealand 30")
                .description("Land of the Daredevils")
                .secret("You should not see this")
                .build();

        //save value and store to cache
        TenantIdClientStorage.setTenantId("30");
        try {
            countryService.save(country).getId();
            assertThat(countryService.findByIsoCode("nz").getIsoCode()).isEqualTo("nz");
        } finally {
            TenantIdClientStorage.setTenantId("0");
        }

        //during this access we should get nothing from the cache as it is anotha tenant
        TenantIdClientStorage.setTenantId("40");
        try {
            assertThat(countryService.findByIsoCode("nz")).isNull();
        } finally {
            TenantIdClientStorage.setTenantId("0");
        }
    }
}
