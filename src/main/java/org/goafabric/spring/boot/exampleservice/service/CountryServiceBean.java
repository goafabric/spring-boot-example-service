package org.goafabric.spring.boot.exampleservice.service;

import lombok.experimental.Delegate;
import org.goafabric.spring.boot.exampleservice.logic.CountryLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@RestController
@PreAuthorize("hasRole('STANDARD_ROLE')")
public class CountryServiceBean implements CountryService {
    @Autowired
    @Delegate
    private CountryLogic countryLogic;
}
