package org.goafabric.spring.boot.exampleservice.logic;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.exampleservice.adapter.CalleeServiceAdapter;
import org.goafabric.spring.boot.exampleservice.configuration.CacheConfiguration;
import org.goafabric.spring.boot.exampleservice.crossfunctional.DurationLog;
import org.goafabric.spring.boot.exampleservice.persistence.repository.CountryRepository;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@Slf4j
@Component
@Transactional
@DurationLog
@CacheConfig(cacheNames = {CacheConfiguration.COUNTRIES})
public class CountryLogic {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    @Lazy
    private CalleeServiceAdapter calleeServiceAdapter;

    @Cacheable
    public Country getById(@NonNull final String id) {
        return countryMapper.map(
            countryRepository.getOne(id));
    }

    @Cacheable
    public Country findByIsoCode(@NonNull final String isoCode) {
        return countryMapper.map(
            countryRepository.findByIsoCode(isoCode));
    }

    @Cacheable
    public Country findByName(@NonNull final String name) {
        return countryMapper.map(
                countryRepository.findByName(name));
    }

    public List<Country> findAll() {
        return countryMapper.map(
                countryRepository.findAll());
    }

    @CacheEvict(allEntries = true)
    public Country save(@NonNull final Country country) {
        return countryMapper.map(
            countryRepository.save(
                    countryMapper.map(country)));
    }

    @CacheEvict(allEntries = true)
    public void delete(@NonNull final String id) {
        countryRepository.deleteById(id);
    }

    public Boolean isAlive() {
        return calleeServiceAdapter.isAlive();
    }
}

