package org.goafabric.spring.boot.exampleservice.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class EncryptionConfiguration {
    @Autowired
    private ConfigurationRepository configurationRepository;

    @Bean //just so we don't have to put the bean name inside application.yml
    public StringEncryptor jasyptStringEncryptor() {
        return stringEncryptor();
    }

    @Bean
    public HibernatePBEStringEncryptor hibernateEncryptor() {
        final HibernatePBEStringEncryptor encryptor = new HibernatePBEStringEncryptor();
        encryptor.setEncryptor(stringEncryptor());
        encryptor.setRegisteredName("hibernateEncryptor");
        return encryptor;
    }

    @Bean
    public PBEStringEncryptor stringEncryptor() {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(new String(Base64Utils.decodeFromString(passPhrase())));
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setSaltGenerator(new RandomSaltGenerator());
        encryptor.setIvGenerator(new RandomIvGenerator());
        return encryptor;
    }

    //reads the passphrase from the database configuration table or inits with a new one
    //if this is somehow not possible, you could just read from application yml, which is less secure ( @Value("${security.encryption.passphrase}" )
    @Bean
    @Transactional
    public String passPhrase() {
        final String passphrase =
                Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        final Optional<ConfigurationRepository.ConfigurationBo> configuration
                = configurationRepository.findById("passphrase");
        return configuration.isPresent()
                ? configuration.get().getConfigValue()
                : configurationRepository.save(ConfigurationRepository.ConfigurationBo.builder()
                    .configKey("passphrase").configValue(passphrase).build()).getConfigValue();
    }

    //public String passPhrase(@Value("${security.encryption.passphrase}") String passPhrase) { return passPhrase; }
}
