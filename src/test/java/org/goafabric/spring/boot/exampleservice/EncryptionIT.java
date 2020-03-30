package org.goafabric.spring.boot.exampleservice;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

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

    @Test
    public void testBase64Encode() {
        log.info(new String(Base64Utils.encode("cdKBo95xcTVVH3dh".getBytes())));
    }

    @Test
    public void testBase64Decode() {
        log.info(new String(Base64Utils.decodeFromString("Y2RLQm85NXhjVFZWSDNkaA==")));
    }


}
