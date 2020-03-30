package org.goafabric.spring.boot.exampleservice;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class EncryptionIT {
    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void testEncryption() {
        log.info(encryptor.encrypt("secret"));
    }
}
