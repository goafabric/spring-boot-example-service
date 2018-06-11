package org.goafabric.spring.boot.exampleservice.service;

import lombok.experimental.Delegate;
import org.goafabric.spring.boot.exampleservice.logic.CountryLogicBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */
public class CountryServiceBean implements CountryService {
    @Delegate
    @Autowired
    private CountryLogicBean countryLogicBean;
}
