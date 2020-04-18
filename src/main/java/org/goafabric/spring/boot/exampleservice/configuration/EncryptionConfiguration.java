package org.goafabric.spring.boot.exampleservice.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEStringEncryptor;
import org.jasypt.salt.StringFixedIVGenerator;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class EncryptionConfiguration {
    @Autowired
    ConfigurationRepository configurationRepository;

    @Bean
    public StringEncryptor stringEncryptor() {
        return jasyptStringEncryptor();
    }

    @Bean
    public HibernatePBEStringEncryptor hibernateEncryptor() {
        HibernatePBEStringEncryptor encryptor = new HibernatePBEStringEncryptor();
        encryptor.setEncryptor(jasyptStringEncryptor());
        encryptor.setRegisteredName("hibernateEncryptor");
        return encryptor;
    }

    @Bean
    public PBEStringEncryptor jasyptStringEncryptor() {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        String iv = "0Yo6wn3UNyszXrAtV9KOl0SWEKYf8feYjv7dwWIobCXEuMz8t88xahe2IujJsjrWcZXjs6RNAUYh1FmKn3p3wMFWGy6MmK1YWWGCGv7jxaZVr2hXhuOohEdr823aaad4";
        StringFixedIVGenerator stringFixedIVGenerator = new StringFixedIVGenerator(iv);
        encryptor.setIVGenerator(stringFixedIVGenerator);
        encryptor.setSaltGenerator(new StringFixedSaltGenerator(iv));
        encryptor.setPassword(new String(Base64Utils.decodeFromString(passPhrase())));;                        // we HAVE TO set a password

        return encryptor;
    }

    @Bean
    @Transactional
    public String passPhrase() {
        final String passphrase =
                new String(Base64Utils.encode(UUID.randomUUID().toString().getBytes()));
        final Optional<ConfigurationRepository.ConfigurationBo> configuration
                = configurationRepository.findById("passphrase");
        return configuration.isPresent()
                ? configuration.get().getConfigValue()
                : configurationRepository.save(ConfigurationRepository.ConfigurationBo.builder()
                    .configKey("passphrase").configValue(passphrase).build()).getConfigValue();
    }

    /* Alternative if reading from a database is not an option
    @Bean
    public String passPhrase(@Value("${security.encryption.passphrase}") String passPhrase) {
        return passPhrase;
    }
    */
}
