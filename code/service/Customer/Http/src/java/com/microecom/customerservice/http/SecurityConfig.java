package com.microecom.customerservice.http;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer().jwt();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/rest/V1/customer").permitAll();
        http.authorizeRequests().antMatchers("/rest/V1/customer").authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();
        http.formLogin().disable();
        http.httpBasic().disable();
        http.rememberMe().disable();
        http.logout().disable();
    }
}
