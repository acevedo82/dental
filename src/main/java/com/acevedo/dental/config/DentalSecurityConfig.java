package com.acevedo.dental.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class DentalSecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Configurar REST con autenticacion
		http.httpBasic()
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET).hasRole("USER")
			.antMatchers(HttpMethod.POST).hasRole("USER")
			.antMatchers(HttpMethod.PUT).hasRole("USER")
			.antMatchers(HttpMethod.DELETE).hasRole("USER")
			.and()
			.csrf().disable();			
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.inMemoryAuthentication()
			.withUser("dentalmundokids").password("{noop}password").roles("USER")
			.and()
			.withUser("admin").password("{noop}Smetmm5b").roles("USER", "ADMIN");
	}

	
	
}
