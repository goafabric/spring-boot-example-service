package org.goafabric.spring.boot.exampleservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = SpringDataConfiguration.BASE_PACKAGE)
//@Import(FlywayAutoConfiguration.class)
public class SpringDataConfiguration {
    public static final String BASE_PACKAGE = "org.goafabric";

    /*
    @Bean
    public DataSource dataSource(
            @Value("${db.username}") String username, @Value("${db.password}") String password,
            @Value("${db.url}") String dbUrl, @Value("${db.poolsize}") Integer dbPoolSize) {
        final HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(dbUrl);
        config.setConnectionTestQuery("SELECT 1 ");
        config.setMaximumPoolSize(dbPoolSize);
        return new HikariDataSource( config );
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan(BASE_PACKAGE);
        factoryBean.setDataSource(dataSource);
        return factoryBean;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }
    */
}