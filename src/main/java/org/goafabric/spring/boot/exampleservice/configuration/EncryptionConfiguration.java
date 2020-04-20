package org.goafabric.spring.boot.exampleservice.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEStringEncryptor;
import org.jasypt.salt.RandomIVGenerator;
import org.jasypt.salt.RandomSaltGenerator;
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
    private ConfigurationRepository configurationRepository;

    @Bean
    public StringEncryptor stringEncryptor() {
        return jasyptStringEncryptor();
    }

    @Bean
    public HibernatePBEStringEncryptor hibernateEncryptor() {
        final HibernatePBEStringEncryptor encryptor = new HibernatePBEStringEncryptor();
        encryptor.setEncryptor(jasyptStringEncryptor());
        encryptor.setRegisteredName("hibernateEncryptor");
        return encryptor;
    }

    @Bean
    public PBEStringEncryptor jasyptStringEncryptor() {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(new String(Base64Utils.decodeFromString(passPhrase())));
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setSaltGenerator(new RandomSaltGenerator());
        encryptor.setIVGenerator(new RandomIVGenerator());
        return encryptor;
    }

    //reads the passphrase from the database or inits with a new one
    //if this is somehow not possible, you could just read from application yml, which is less secure ( @Value("${security.encryption.passphrase}" )
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
}
