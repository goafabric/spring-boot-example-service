package org.goafabric.spring.boot.exampleservice.service;

import org.goafabric.spring.boot.exampleservice.crossfunctional.HostNameInfoActuator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class DbIT {
    @Autowired
    private HostNameInfoActuator hostNameInfoActuator;

    @Test
    public void getStatStements() {
        System.out.println(hostNameInfoActuator.getStatSatements());
    }
}
