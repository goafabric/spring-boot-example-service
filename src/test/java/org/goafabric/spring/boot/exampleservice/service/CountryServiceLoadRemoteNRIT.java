package org.goafabric.spring.boot.exampleservice.service;

import lombok.extern.slf4j.Slf4j;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.goafabric.spring.boot.exampleservice.client.CountryServiceClient;
import org.goafabric.spring.boot.exampleservice.configuration.RestTemplateConfiguration;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@Import(RestTemplateConfiguration.class)
@RunWith(SpringRunner.class)
@Slf4j
public class CountryServiceLoadRemoteNRIT {
    @Autowired
    private RestTemplate restTemplate;

    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    private CountryServiceClient countryService;

    final String port = "50700";

    @PostConstruct
    private void init() {
        this.countryService
                = new CountryServiceClient(restTemplate, "http://localhost:" + port);
    }

    @PerfTest(invocations=500000, threads=1)
    @Test
    public void testGetAllCountries() {
        log.info("test");
        final List<Country> countries = countryService.findAll();
        assertThat(countries).isNotNull().isNotEmpty();
    }

}
