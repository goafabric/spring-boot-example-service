package org.goafabric.spring.boot.exampleservice.persistence;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseProvisioning implements CommandLineRunner {

    @Autowired
    private ApplicationContext  applicationContext;

    @Value("${database.provisioning.demo-data-location}")
    private String demoDataLocation;

    @Value("${database.provisioning.goals:-migrate}")
    private String goals;

    @Configuration
    class FlywayConfiguration {
        @Bean
        public FlywayMigrationStrategy flywayMigrationStrategy() {
            return flyway -> {
                flyway = configureDemoDataImport(flyway);
                migrate(flyway);
            };
        }

        private Flyway configureDemoDataImport(Flyway flyway) {
            if (goals.contains("-import-demo-data")) {
                log.info("importing demo data");
                flyway = Flyway.configure().configuration(flyway.getConfiguration())
                        .locations("classpath:db/migration", demoDataLocation).load();
            }
            return flyway;
        }

        private void migrate(Flyway flyway) {
            if (goals.contains("-migrate")) {
                log.info("calling flyway migration");
                flyway.migrate();
            }
        }
    }

    @Override
    public void run(String... args) {
        importCatalogData(goals);
    }

    private void importCatalogData(String goals) {
        if (goals.contains("-import-catalog-data")) {
            log.info("calling catalog data import");
            //do catalog stuff here
        }
    }

    private void terminate(String goals) {
        if (goals.contains("-terminate")) {
            log.info("terminating app");
            SpringApplication.exit(applicationContext, () -> 0); //if an exception is raised, spring will automatically terminate with 1
        }
    }
}

