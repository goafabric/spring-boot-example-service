package org.goafabric.spring.boot.exampleservice.persistence.provisioning;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DatabaseProvisioning implements CommandLineRunner {

    @Autowired
    private ApplicationContext  applicationContext;

    @Autowired
    private DatabaseImport      databaseImport;

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            final String parameters = getGoals();
            if (parameters.contains("-migrate")) {
                log.info("calling flyway migration");
                flyway.migrate();
            }

            if (parameters.contains("-clean")) {
                log.info("calling flyway clean");
                flyway.clean();
            }
        };
    }

    @Override
    public void run(String... args) throws Exception {
        final String parameters = getGoals();
        if (parameters.contains("-import")) {
            log.info("calling catalog import");
            databaseImport.run();
        }

        //TODO: also for Demodata

        //if an exception is thrown, application will terminate anyway
        if (parameters.contains("-terminate")) {
            log.info("terminating app");
            initiateShutdown(0);
        }
    }

    private String getGoals() {
        final String goals = applicationContext.getEnvironment().getProperty("database.provisioning.goal");
        return goals;
    }

    public void initiateShutdown(int exitCode){
        SpringApplication.exit(applicationContext, () -> exitCode);
    }
}

