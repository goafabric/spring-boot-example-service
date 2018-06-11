package org.goafabric.spring.boot.exampleservice.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@Slf4j
@Component
public class CountryLogicBean {
    public void getCountry() {
        log.info("yo");
    }
}
