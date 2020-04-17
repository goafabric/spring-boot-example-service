package org.goafabric.spring.boot.exampleservice.configuration;

import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEStringEncryptor;
import org.jasypt.salt.RandomIVGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;

@Configuration
public class EncryptionConfiguration {
    @Value("${jasypt.encryptor.passphrase}")
    private String passPhrase;

    @Bean
    public PBEStringEncryptor passwordEncryptor() {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(new String(Base64Utils.decodeFromString(passPhrase)));;                        // we HAVE TO set a password
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIVGenerator(new RandomIVGenerator());
        return encryptor;
    }

    @Bean
    public HibernatePBEStringEncryptor hibernateEncryptor() {
        HibernatePBEStringEncryptor encryptor = new HibernatePBEStringEncryptor();
        encryptor.setEncryptor(passwordEncryptor());
        encryptor.setRegisteredName("hibernateEncryptor");
        return encryptor;
    }
}
