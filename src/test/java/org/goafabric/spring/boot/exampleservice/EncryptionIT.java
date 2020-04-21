package org.goafabric.spring.boot.exampleservice;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class EncryptionIT {
    @Autowired
    private PasswordEncoder passwordHashEncoder;

    @Autowired
    private StringEncryptor stringEncryptor;

    //1-way hash, cannot be converted back, only matching possible
    @Test
    public void testPasswordHashEncode() {
        log.info(passwordHashEncoder.encode("admin"));
    }

    @Test
    public void testPasswordHashMatch() {
        log.info("" + passwordHashEncoder.matches("admin",
                "$2a$10$onJqryBEk9ToQSVPMBHTOO5PaXZXvkztWXDQqzkC4d.ORlMpt8Y4G"));
    }

    //2-way encryption and decryption
    @Test
    public void testAES256Encryption() {
        log.info(stringEncryptor.encrypt("topsecret"));
        log.info(stringEncryptor.encrypt("topsecret"));
        log.info(stringEncryptor.encrypt("topsecret"));
    }

    @Test
    public void testAES256Decryption() {
        log.info(stringEncryptor.decrypt("LgCT4krW5NDrQkfGPPEYw7XOvzufvIANWFq3vHow+Sphd6ACtavahW8aL28NDnmZex5+Pnz14NEeDuR+ZI90sQ=="));
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

    @Value("${adapter.calleeservice.password}")
    private String password;

    @Test
    public void testShowEncryptedPassword() {
        log.info(password);
    }

}
