package org.goafabric.spring.boot.exampleservice.configuration;

import org.goafabric.spring.boot.exampleservice.persistence.domain.ConfigurationBo;
import org.goafabric.spring.boot.exampleservice.persistence.repository.ConfigurationRepository;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEStringEncryptor;
import org.jasypt.salt.RandomIVGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.util.Optional;

@Configuration
public class EncryptionConfiguration {
    //@Value("${jasypt.encryptor.passphrase}")
    //private String passPhrase;

    @Autowired
    ConfigurationRepository configurationRepository;

    @Bean
    public PBEStringEncryptor jasyptStringEncryptor() {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(new String(Base64Utils.decodeFromString(passPhrase())));;                        // we HAVE TO set a password
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIVGenerator(new RandomIVGenerator());
        return encryptor;
    }

    @Bean
    public HibernatePBEStringEncryptor hibernateEncryptor() {
        HibernatePBEStringEncryptor encryptor = new HibernatePBEStringEncryptor();
        encryptor.setEncryptor(jasyptStringEncryptor());
        encryptor.setRegisteredName("hibernateEncryptor");
        return encryptor;
    }

    @Bean
    @Transactional
    public String passPhrase() {
        configurationRepository.save(ConfigurationBo.builder()
                        .config_key("passphrase").config_value(
                            "Y2RLQm85NXhjVFZWSDNkaA==").build());
        Optional<ConfigurationBo> configurationBo = configurationRepository.findById("passphrase");
        return configurationBo.get().getConfig_value();
    }
}
