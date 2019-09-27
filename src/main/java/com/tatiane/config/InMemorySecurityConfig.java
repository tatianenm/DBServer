package com.tatiane.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
public class InMemorySecurityConfig {

    @Autowired
    public void ConfigureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.
                inMemoryAuthentication().
                withUser("jose").password("{noop}123").roles("PG_RESTAURANTE", "PG_FUNCIONARIO", "PG_VOTACAO").
                and().
                withUser("joao").password("{noop}123").roles("PG_RESTAURANTE", "PG_FUNCIONARIO", "PG_VOTACAO").
                and().
                withUser("marcos").password("{noop}123").roles("PG_RESTAURANTE", "PG_FUNCIONARIO", "PG_VOTACAO").
                and().
                withUser("patricia").password("{noop}123").roles("PG_RESTAURANTE", "PG_FUNCIONARIO", "PG_VOTACAO").
                and().
                withUser("susi").password("{noop}123").roles("PG_RESTAURANTE", "PG_FUNCIONARIO", "PG_VOTACAO").
                and().
                withUser("andre").password("{noop}123").roles("PG_RESTAURANTE", "PG_FUNCIONARIO", "PG_VOTACAO").
                and().
                withUser("pedro").password("{noop}123").roles("PG_RESTAURANTE", "PG_FUNCIONARIO", "PG_VOTACAO");
    }

}
