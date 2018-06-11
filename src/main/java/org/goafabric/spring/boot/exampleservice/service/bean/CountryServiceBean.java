package org.goafabric.spring.boot.exampleservice.service.bean;

import lombok.experimental.Delegate;
import org.goafabric.spring.boot.exampleservice.logic.CountryLogicBean;
import org.goafabric.spring.boot.exampleservice.service.CountryService;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */
public class CountryServiceBean implements CountryService {
    @Delegate
    private CountryLogicBean countryLogicBean;
}
