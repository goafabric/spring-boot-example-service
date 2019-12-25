
package org.goafabric.spring.boot.exampleservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(value = "security.authentication.enabled", matchIfMissing = false)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${security.credentials.user}")
    private String user;

    @Value("${security.credentials.password}")
    private String password;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        final String password = PasswordEncoderFactories
                .createDelegatingPasswordEncoder()
                .encode("admin");

        System.out.println(password);
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(password)
                .roles("STANDARD_ROLE");
    }


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

}