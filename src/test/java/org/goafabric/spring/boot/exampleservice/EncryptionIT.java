package org.goafabric.spring.boot.exampleservice;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class EncryptionIT {
    @Autowired
    private PasswordEncoder passwordHash;

    @Autowired
    private StringEncryptor passwordEncryptor;

    //1-way hash, cannot be converted back, only matchin possible
    @Test
    public void testPasswordHashEncode() {
        log.info(passwordHash.encode("secret"));
    }

    @Test
    public void testPasswordHashMatch() {
        log.info("" + passwordHash.matches("secret",
                "$2a$10$q1qtofwdHL7gcJeHiKSVx.I6lImNQVWzO01S13bajJu2USH9JCRX6"));
    }

    //2-way encryption and decryption
    @Test
    public void testAES256Encryption() {
        log.info(passwordEncryptor.encrypt("secret"));
    }

    @Test
    public void testAES256Decryption() {
        log.info(passwordEncryptor.decrypt("g2DWwx+uQYLX9vEOVUkb2fFu5ApHt/jXVADJVVmqjlvn0OEiA6rgv9Yz3DfiNJby"));
    }

    //2-way base64 encoding
    @Test
    public void testBase64Encode() {
        log.info(new String(Base64Utils.encode("cdKBo95xcTVVH3dh".getBytes())));
    }

    @Test
    public void testBase64Decode() {
        log.info(new String(Base64Utils.decodeFromString("Y2RLQm85NXhjVFZWSDNkaA==")));
    }


}
