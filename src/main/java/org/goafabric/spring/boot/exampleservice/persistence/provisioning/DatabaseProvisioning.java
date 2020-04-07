package org.goafabric.spring.boot.exampleservice.persistence.provisioning;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseProvisioning implements CommandLineRunner {

    @Autowired
    private ApplicationContext  applicationContext;

    @Autowired(required = false)
    private DatabaseImport      databaseImport;

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            doFlyway(flyway);
        };
    }

    @Override
    public void run(String... args) throws Exception {
        final String goals = getGoals();
        doImport(goals);

        if (goals.contains("-terminate")) {
            log.info("terminating app");
            initiateShutdown(0);
        }
    }

    private void doFlyway(Flyway flyway) {
        final String goals = getGoals();
        if (goals.contains("-migrate")) {
            log.info("calling flyway migration");
            flyway.migrate();
        }

        if (goals.contains("-clean")) {
            log.info("calling flyway clean");
            flyway.clean();
        }
    }

    private void doImport(String goals) {
        if (databaseImport != null) {
            if (goals.contains("-import-catalog-data")) {
                log.info("calling catalog data import");
                databaseImport.importCatalogData();
            }

            if (goals.contains("-import-demo-data")) {
                log.info("calling demo data import");
                databaseImport.importDemoData();
            }
        }
    }

    private String getGoals() {
        final String goals = applicationContext.getEnvironment().getProperty("database.provisioning.goals");
        return (StringUtils.isNullOrEmpty(goals) == true)
                ? "-migrate" : goals;
    }

    public void initiateShutdown(int exitCode){
        SpringApplication.exit(applicationContext, () -> exitCode);
    }
}

