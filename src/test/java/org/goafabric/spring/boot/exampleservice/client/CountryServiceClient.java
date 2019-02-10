package org.goafabric.spring.boot.exampleservice.client;

import org.goafabric.spring.boot.exampleservice.service.CountryService;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CountryServiceClient implements CountryService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUri;
    private final String serviceUri;

    public CountryServiceClient(String baseUri) {
        this.baseUri = baseUri;
        this.serviceUri = baseUri + CountryService.RESOURCE;
    }

    @Override
    public Country getById(String id) {
        return restTemplate.getForObject(serviceUri + "/getById/{id}",
                Country.class, id);
    }

    @Override
    public List<Country> findAll() {
        return restTemplate.getForObject(serviceUri + "/findall",
                List.class);
    }

    @Override
    public Country findByIsoCode(String isoCode) {
        return restTemplate.getForObject(serviceUri + "/findByIsoCode",
                Country.class, isoCode);
    }

    @Override
    public Country findByName(String name) {
        return restTemplate.getForObject(serviceUri + "/findName",
                Country.class, name);
    }

    @Override
    public Country save(Country country) {
        return restTemplate.postForObject(serviceUri + "/save",
                country, Country.class);
    }

    @Override
    public void delete(String id) {
        restTemplate.delete(serviceUri + "/delete",
                id);
    }
}
