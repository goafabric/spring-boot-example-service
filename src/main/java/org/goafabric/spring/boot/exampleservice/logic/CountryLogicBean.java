package org.goafabric.spring.boot.exampleservice.logic;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.exampleservice.mapper.CountryMapper;
import org.goafabric.spring.boot.exampleservice.persistence.repository.CountryRepository;
import org.goafabric.spring.boot.exampleservice.service.dto.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@Slf4j
@Component
@Transactional
public class CountryLogicBean {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMapper countryMapper;

    public Country getById(@NonNull final String id) {
        return countryMapper.toDto(
            countryRepository.getOne(id));
    }


    public List<Country> findAll() {
        return countryMapper.toDtos(
            countryRepository.findAll());
    }

    public Country findByIsoCode(@NonNull final String isoCode) {
        return countryMapper.toDto(
            countryRepository.findByIsoCode(isoCode));
    }

    public Country findByName(@NonNull final String name) {
        return countryMapper.toDto(
                countryRepository.findByName(name));
    }

    public Country save(@NonNull final Country country) {
        return countryMapper.toDto(
            countryRepository.save(
                    countryMapper.toBo(country)));
    }

    public void delete(@NonNull final String id) {
        countryRepository.deleteById(id);
    }

}

