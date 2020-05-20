package com.microecom.orderservice.http;

import com.microecom.orderservice.http.model.PrincipalManager;
import com.microecom.orderservice.http.model.principal.CompositePrincipalManager;
import com.microecom.orderservice.http.model.principal.JwtAwarePrincipalManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.HashSet;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .rememberMe().disable()
                .formLogin().disable()
                .logout().disable()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/rest/V1/order").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/rest/V1/order/**").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.PUT, "/rest/V1/order/*/payment-details/**").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/rest/V1/order").authenticated();
        http.oauth2ResourceServer().jwt();
    }

    @Bean
    public PrincipalManager principalManager(JwtAwarePrincipalManager jwtManager) {
        var set = new HashSet<PrincipalManager>();
        set.add(jwtManager);
        return new CompositePrincipalManager(set);
    }
}
