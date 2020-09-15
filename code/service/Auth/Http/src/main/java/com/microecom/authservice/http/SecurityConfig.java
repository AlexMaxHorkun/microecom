package com.microecom.authservice.http;

import com.microecom.authservice.http.model.UserDetailsRetriever;
import com.microecom.authservice.model.DefaultPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsRetriever userDetailsRetriever;

    private final DefaultPasswordEncryptor passwordEncryptor;

    protected SecurityConfig(
            @Autowired UserDetailsRetriever userDetailsRetriever,
            @Autowired DefaultPasswordEncryptor passwordEncryptor
    ) {
        super();
        this.userDetailsRetriever = userDetailsRetriever;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .rememberMe().disable()
                .formLogin().disable()
                .logout().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsRetriever);
        provider.setPasswordEncoder(passwordEncryptor);
        auth.authenticationProvider(provider);
    }
}
