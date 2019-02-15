package org.goafabric.spring.boot.exampleservice.client;

import org.goafabric.spring.boot.exampleservice.service.CountryService;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CountryServiceClient implements CountryService {
    @Autowired
    private RestTemplate restTemplate;

    private final String serviceUri;

    public CountryServiceClient(String baseUri) {
        this.serviceUri = baseUri + CountryService.RESOURCE;
    }

    @Override
    public Country getById(String id) {
        return restTemplate.getForObject(serviceUri + "/getById/{id}",
                Country.class, id);
    }

    @Override
    public List<Country> findAll() {
        return restTemplate.getForObject(serviceUri + "/findAll",
                List.class);
    }

    @Override
    public Country findByIsoCode(String isoCode) {
        return restTemplate.getForObject(serviceUri + "/findByIsoCode?isoCode={isoCode}",
                Country.class, isoCode);
    }

    @Override
    public Country findByName(String name) {
        return restTemplate.getForObject(serviceUri + "/findByName?name={name}",
                Country.class, name);
    }

    @Override
    public Country save(Country country) {
        return restTemplate.postForObject(serviceUri + "/save",
                country, Country.class);
    }

    @Override
    public void delete(String id) {
        restTemplate.delete(serviceUri + "/delete?id={id}",
                id);
    }
}
