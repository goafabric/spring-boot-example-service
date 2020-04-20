
package org.goafabric.spring.boot.exampleservice.configuration;

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


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(value = "security.authentication.enabled")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override //in memory authentication
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        //in memory authentication
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordHashEncoder())
                .withUser("admin")
                .password("$2a$10$onJqryBEk9ToQSVPMBHTOO5PaXZXvkztWXDQqzkC4d.ORlMpt8Y4G")
                .roles("STANDARD_ROLE");

        //database authentication
        //auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordHashEncoder());
    }

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
    public PasswordEncoder passwordHashEncoder(){
        return new BCryptPasswordEncoder();
    }
}
