package org.goafabric.spring.boot.exampleservice.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate5.encryptor.HibernatePBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.jasypt.iv.StringFixedIvGenerator;
import org.jasypt.salt.RandomSaltGenerator;
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
    public HibernatePBEStringEncryptor hibernateEncryptor() {
        final HibernatePBEStringEncryptor encryptor = new HibernatePBEStringEncryptor();
        encryptor.setEncryptor(databaseEncryptor());
        encryptor.setRegisteredName("hibernateEncryptor");
        return encryptor;
    }

    @Bean
    @Transactional
    public PBEStringEncryptor propertyEncryptor() {
        return createEncryptor("property_passphrase");
    }

    @Bean
    @Transactional
    public PBEStringEncryptor databaseEncryptor() {
        return createEncryptor("database_passphrase");
    }

    private StandardPBEStringEncryptor createEncryptor(String key) {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(new String(Base64Utils.decodeFromString(getPassPhrase(key))));
        encryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        encryptor.setSaltGenerator(new RandomSaltGenerator());
        encryptor.setIvGenerator(new RandomIvGenerator());
        return encryptor;
    }

    /*
    @Bean
    public PBEStringEncryptor jasyptStringEncryptor() {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        String iv = "0Yo6wn3UNyszXrAtV9KOl0SWEKYf8feYjv7dwWIobCXEuMz8t88xahe2IujJsjrWcZXjs6RNAUYh1FmKn3p3wMFWGy6MmK1YWWGCGv7jxaZVr2hXhuOohEdr823aaad4";
        StringFixedIvGenerator stringFixedIVGenerator = new StringFixedIvGenerator(iv);
        encryptor.setIvGenerator(stringFixedIVGenerator);
        encryptor.setSaltGenerator(new StringFixedSaltGenerator(iv));
        encryptor.setPassword(new String(Base64Utils.decodeFromString(passPhrase())));;                        // we HAVE TO set a password

        return encryptor;
    }
    */


    //reads the passphrase from the database configuration table or inits with a new one
    //if this is somehow not possible, you could just read from application yml, which is less secure ( @Value("${security.encryption.passphrase}" )
    public String getPassPhrase(String key) {
        final String passphrase =
                Base64Utils.encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        final Optional<ConfigurationRepository.ConfigurationBo> configuration
                = configurationRepository.findById(key);
        return configuration.isPresent()
                ? configuration.get().getConfigValue()
                : configurationRepository.save(ConfigurationRepository.ConfigurationBo.builder()
                    .configKey("passphrase").configValue(passphrase).build()).getConfigValue();
    }
}
