package com.microecom.customerservice.http;

import com.microecom.customerservice.http.model.PrincipalManager;
import com.microecom.customerservice.http.model.principal.CompositePrincipalManager;
import com.microecom.customerservice.http.model.principal.JwtAwarePrincipalManager;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
                .authorizeRequests().antMatchers(HttpMethod.POST, "/rest/V1/customer").permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/rest/V1/customer/**").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.PATCH, "/rest/V1/customer/**").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.DELETE, "/rest/V1/customer/**").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/rest/V1/address").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.PATCH, "/rest/V1/address/**").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.DELETE, "/rest/V1/address/**").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/rest/V1/address").authenticated();
        http.oauth2ResourceServer().jwt();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.debug(true);
    }

    @Bean
    public PrincipalManager principalManager(JwtAwarePrincipalManager jwtManager) {
        var set = new HashSet<PrincipalManager>();
        set.add(jwtManager);
        return new CompositePrincipalManager(set);
    }
}
