
package org.goafabric.spring.boot.exampleservice.configuration;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(value = "security.authentication.enabled", matchIfMissing = false)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override //in memory authentication
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("STANDARD_ROLE");
    }

    /*
    @Override //database authentication
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder());
    }
    */

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers(
                        "/actuator/**",
                        "/", "/welcome/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public StringEncryptor passwordEncryptor(@Value("${encryption.passphrase}") String passPhrase) {
        final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        final SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(passPhrase);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setPoolSize("1");
        encryptor.setConfig(config);
        return encryptor;
    }
}
