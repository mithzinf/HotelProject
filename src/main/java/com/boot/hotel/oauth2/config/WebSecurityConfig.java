package com.boot.hotel.oauth2.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


import lombok.RequiredArgsConstructor;

@Configurable
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		
		http.
			csrf().disable().headers().frameOptions().disable()
			.and()
			.authorizeRequests()
				.antMatchers("/**").permitAll()
			.and()
			.authorizeRequests()
			.anyRequest().authenticated();
		
		
		
		return http.build();
	}
	
	
}
