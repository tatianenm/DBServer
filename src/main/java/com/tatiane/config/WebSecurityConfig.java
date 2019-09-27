package com.tatiane.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().//para corrigir o forbidden
                authorizeRequests().
                antMatchers("/retaurante").hasAnyRole("PG_RESTAURANTE").
                antMatchers("/funcionario").hasAnyRole("PG_FUNCIONARIO").
                antMatchers("/votacao").hasAnyRole("PG_VOTACAO").
                anyRequest().
                authenticated().
                and().formLogin();

    }

}
