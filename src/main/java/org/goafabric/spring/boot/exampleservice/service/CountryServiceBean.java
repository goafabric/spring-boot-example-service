package org.goafabric.spring.boot.exampleservice.service;

import lombok.experimental.Delegate;
import org.goafabric.spring.boot.exampleservice.logic.CountryLogicBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@RestController
public class CountryServiceBean implements CountryService {
    @Autowired
    @Delegate
    private CountryLogicBean countryLogicBean;
}
