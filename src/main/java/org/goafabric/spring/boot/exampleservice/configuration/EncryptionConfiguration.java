package org.goafabric.spring.boot.exampleservice.configuration;

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
        return propertyEncryptor();
    }

    @Bean
    @Transactional
    public PBEStringEncryptor propertyEncryptor() {
        return getAES256Encryptor("property_passphrase",
                new RandomIvGenerator(), new RandomSaltGenerator());
    }

    @Bean
    @Transactional
    public PBEStringEncryptor databaseEncryptor() {
        return getAES256Encryptor("database_passphrase",
                new RandomIvGenerator(), new RandomSaltGenerator());
    }

    @Bean
    public PBEStringEncryptor databaseSearchableEncryptor() {
        final String iv = getConfigValue("database_iv");
        final String salt = getConfigValue("database_salt");
        return getAES256Encryptor("database_passphrase",
                new StringFixedIvGenerator(iv), new StringFixedSaltGenerator(salt));
    }

    private StandardPBEStringEncryptor getAES256Encryptor(String configKey, IvGenerator ivGenerator, SaltGenerator saltGenerator) {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setIvGenerator(ivGenerator);
        encryptor.setSaltGenerator(saltGenerator);
        encryptor.setPassword(getConfigValue(configKey));
        return encryptor;
    }

    @Bean
    public HibernatePBEStringEncryptor hibernateEncryptor() {
        final HibernatePBEStringEncryptor encryptor = new HibernatePBEStringEncryptor();
        encryptor.setEncryptor(databaseEncryptor());
        encryptor.setRegisteredName("hibernateStringEncryptor");
        return encryptor;
    }

    @Bean
    public HibernatePBEStringEncryptor hibernateSearchableEncryptor() {
        final HibernatePBEStringEncryptor encryptor = new HibernatePBEStringEncryptor();
        encryptor.setEncryptor(databaseSearchableEncryptor());
        encryptor.setRegisteredName("hibernateSearchableStringEncryptor");
        return encryptor;
    }

    //reads the passphrase from the database configuration table or inits with a new one
    //if this is somehow not possible, you could just read from application yml, which is less secure ( @Value("${security.encryption.passphrase}" )
    public String getConfigValue(String configKey) {
        final Optional<ConfigurationRepository.ConfigurationBo> configuration
                = configurationRepository.findById(configKey);

        return configuration.isPresent()
                ? new String (Base64Utils.decodeFromString(configuration.get().getConfigValue()))
                : configurationRepository.save(ConfigurationRepository.ConfigurationBo.builder()
                    .configKey(configKey).configValue(generateUniqueId()).build()).getConfigValue();
    }

    private String generateUniqueId() {
        return Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
    }
}
