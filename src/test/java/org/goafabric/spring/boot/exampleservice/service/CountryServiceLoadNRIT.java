package org.goafabric.spring.boot.exampleservice.service;

import lombok.extern.slf4j.Slf4j;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.goafabric.spring.boot.exampleservice.client.CountryServiceClient;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest//(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class CountryServiceLoadNRIT {
    @Autowired
    private CountryServiceClient countryService;


    @Rule
    public ContiPerfRule rule = new ContiPerfRule();

    @PerfTest(invocations=500000, threads=10)
    @Test
    public void testGetAllCountries() {
        log.info("test");
        final List<Country> countries = countryService.findAll();
        assertThat(countries).isNotNull().isNotEmpty();
    }

}
