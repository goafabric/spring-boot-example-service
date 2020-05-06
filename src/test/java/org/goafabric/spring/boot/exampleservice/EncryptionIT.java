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
    private StringEncryptor propertyEncryptor;

    //1-way hash, cannot be converted back, only matching possible
    @Test
    public void testUserPasswordHashEncode() {
        log.info(passwordHashEncoder.encode("admin"));
    }

    @Test
    public void testUserPasswordHashMatch() {
        log.info("" + passwordHashEncoder.matches("admin",
                "$2a$10$onJqryBEk9ToQSVPMBHTOO5PaXZXvkztWXDQqzkC4d.ORlMpt8Y4G"));
    }

    //2-way encryption and decryption
    @Test
    public void testPropertyAES256Encryption() {
        log.info(propertyEncryptor.encrypt("cdKBo95xcTVVH3dh"));
    }

    @Test
    public void testPropertyAES256Decryption() {
        log.info(propertyEncryptor.decrypt("LgCT4krW5NDrQkfGPPEYw7XOvzufvIANWFq3vHow+Sphd6ACtavahW8aL28NDnmZex5+Pnz14NEeDuR+ZI90sQ=="));
    }

    @Value("${adapter.calleeservice.password}")
    private String password;

    @Test
    public void testShowDecryptedPropertyPassword() {
        log.info(password);
    }

    //2-way base64 encoding
    @Test
    public void testPassphraseBase64Encode() {
        log.info(new String(Base64Utils.encode("d56b2227-14bc-42d9-80f7-0e6c72716662".getBytes())));
    }
    @Test
    public void testPassphraseBase64Decode() {
        log.info(new String(Base64Utils.decodeFromString("ZDU2YjIyMjctMTRiYy00MmQ5LTgwZjctMGU2YzcyNzE2NjYy")));
    }
}
