package org.goafabric.spring.boot.exampleservice.client;

import org.goafabric.spring.boot.exampleservice.service.CountryService;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CountryServiceClient implements CountryService {
    private final RestTemplate restTemplate;

    private final String serviceUrl;

    public CountryServiceClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = baseUrl + CountryService.RESOURCE;
    }

    @Override
    public Country getById(String id) {
        return restTemplate.getForObject(serviceUrl + "/getById/?id={id}",
                Country.class, id);
    }

    @Override
    public List<Country> findAll() {
        return restTemplate.getForObject(serviceUrl + "/findAll",
                List.class);
    }

    @Override
    public Country findByIsoCode(String isoCode) {
        return restTemplate.getForObject(serviceUrl + "/findByIsoCode?isoCode={isoCode}",
                Country.class, isoCode);
    }

    @Override
    public Country findByName(String name) {
        return restTemplate.getForObject(serviceUrl + "/findByName?name={name}",
                Country.class, name);
    }

    @Override
    public Country save(Country country) {
        return restTemplate.postForObject(serviceUrl + "/save",
                country, Country.class);
    }

    @Override
    public void delete(String id) {
        restTemplate.delete(serviceUrl + "/delete?id={id}",
                id);
    }

    @Override
    public Boolean isAlive() {
        return restTemplate.getForObject(serviceUrl + "/isAlive", Boolean.class);
    }
}
