package org.goafabric.spring.boot.exampleservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.goafabric.spring.boot.exampleservice.persistence.domain.ConfigurationBo;
import org.goafabric.spring.boot.exampleservice.persistence.repository.ConfigurationRepository;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEStringEncryptor;
import org.jasypt.iv.IvGenerator;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.iv.StringFixedIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
import org.jasypt.salt.SaltGenerator;
import org.jasypt.salt.StringFixedSaltGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

@Configuration
@Slf4j
public class EncryptionConfiguration {
    @Autowired
    private ConfigurationRepository configurationRepository;

    @Bean //just so we don't have to put the bean name inside application.yml
    public StringEncryptor jasyptStringEncryptor() {
        return propertyEncryptor();
    }

    //property encryptor, always needed
    @Bean
    public PBEStringEncryptor propertyEncryptor() {
        return getAES256Encryptor("property_passphrase",
                new RandomIvGenerator(), new RandomSaltGenerator());
    }

    //database encryptor, with random salt, very secure
    @Bean
    public HibernatePBEStringEncryptor hibernateEncryptor() {
        final HibernatePBEStringEncryptor encryptor = new HibernatePBEStringEncryptor();
        encryptor.setEncryptor(databaseEncryptor());
        encryptor.setRegisteredName("hibernateStringEncryptor");
        return encryptor;
    }

    @Bean
    public PBEStringEncryptor databaseEncryptor() {
        return getAES256Encryptor("database_passphrase",
                new RandomIvGenerator(), new RandomSaltGenerator());
    }

    //searchable encrytor, still secure, but static autogenerated iv + salt per customer/database
    @Bean
    public HibernatePBEStringEncryptor hibernateSearchableEncryptor() {
        final HibernatePBEStringEncryptor encryptor = new HibernatePBEStringEncryptor();
        encryptor.setEncryptor(databaseSearchableEncryptor());
        encryptor.setRegisteredName("hibernateSearchableStringEncryptor");
        return encryptor;
    }

    @Bean
    public PBEStringEncryptor databaseSearchableEncryptor() {
        return getAES256Encryptor("database_passphrase",
                new StringFixedIvGenerator(getConfigValue("database_searchable_iv")),
                        new StringFixedSaltGenerator(getConfigValue("database_searchable_salt")));
    }

    private StandardPBEStringEncryptor getAES256Encryptor(String configKey, IvGenerator ivGenerator, SaltGenerator saltGenerator) {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIvGenerator(ivGenerator);
        encryptor.setSaltGenerator(saltGenerator);
        encryptor.setPassword(getConfigValue(configKey));
        return encryptor;
    }

    //reads the passphrase from the database configuration table or inits with a new one
    //if this is somehow not possible, you could just read from application yml, which is less secure ( @Value("${security.encryption.passphrase}" )
    public String getConfigValue(String configKey) {
        final Optional<ConfigurationBo> configuration
                = configurationRepository.findById(configKey);

        return configuration.isPresent()
                ? new String (Base64Utils.decodeFromString(configuration.get().getConfigValue()))
                : configurationRepository.save(ConfigurationBo.builder()
                    .configKey(configKey).configValue(generateUniqueId()).build()).getConfigValue();
    }

    private String generateUniqueId() {
        return Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
    }
}
