package org.goafabric.spring.boot.exampleservice.persistence.provisioning;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.h2.util.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseProvisioning implements CommandLineRunner {

    @Autowired
    private ApplicationContext  applicationContext;

    @Autowired(required = false)
    private DatabaseImport      databaseImport;

    @Autowired
    @Lazy
    private StringEncryptor propertyEncryptor;

    @Value("${database.provisioning.demo-data-location}")
    private String demoDataLocation;

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return this::doFlyway; //flyway always needs to run this way, otherwise we end up with a fucked up application
    }

    private void doFlyway(Flyway flyway) {
        final String goals = getGoals();
        if (goals.contains("-import-demo-data")) {
            flyway = Flyway.configure().configuration(flyway.getConfiguration())
                    .locations("classpath:db/migration", demoDataLocation).load();
        }

        if (goals.contains("-migrate")) {
            log.info("calling flyway migration");
            flyway.migrate();
        }

        if (goals.contains("-clean")) {
            log.info("calling flyway clean");
            flyway.clean();
        }
    }

    @Override
    public void run(String... args) {
        final String goals = getGoals();
        doImport(goals);
        doEncrypt(goals);
        doTerminate(goals);
    }

    private String getGoals() {
        final String goals = applicationContext.getEnvironment().getProperty("database.provisioning.goals");
        return (StringUtils.isNullOrEmpty(goals))  ? "-migrate" : goals;
    }

    private void doImport(String goals) {
        if (databaseImport != null) {
            if (goals.contains("-import-catalog-data")) {
                log.info("calling catalog data import");
                databaseImport.importCatalogData();
            }
        }
    }

    private void doEncrypt(String goals) {
        if (goals.contains("-encryptproperty=")) {
            final String string = goals.split("-encryptproperty=")[1].split("-terminate")[0];
            log.info(propertyEncryptor.encrypt("encrypted string will be:" + string));
        }
    }

    private void doTerminate(String goals) {
        if (goals.contains("-terminate")) {
            log.info("terminating app");
            initiateShutdown(0);
        }
    }

    public void initiateShutdown(int exitCode){
        SpringApplication.exit(applicationContext, () -> exitCode);
    }
}

