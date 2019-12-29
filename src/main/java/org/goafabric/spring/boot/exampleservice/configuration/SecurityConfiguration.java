
package org.goafabric.spring.boot.exampleservice.configuration;

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
    @Value("${security.credentials.user}")
    private String user;

    @Value("${security.credentials.password}")
    private String password;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        //System.out.println(passwordEncoder().encode("admin"));
        auth.jdbcAuthentication()
                .dataSource(dataSource);
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        //System.out.println(passwordEncoder().encode("admin"));
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("admin")
                .password(password)
                .roles("STANDARD_ROLE");
    }

     */



    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                //.requestMatchers(EndpointRequest.to("actuator", "welcome")).permitAll()
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
}
