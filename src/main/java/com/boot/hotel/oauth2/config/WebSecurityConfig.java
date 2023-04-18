package com.boot.hotel.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.boot.hotel.oauth2.service.HotelCustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configurable
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	@Autowired
	private final HotelCustomOAuth2UserService hotelCustomOAuth2UserService;
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{

		http.
			csrf().disable().headers().frameOptions().disable()
			.and()
			.authorizeRequests()
				.antMatchers("/**","/hotel/**").permitAll()
				.anyRequest().authenticated()
			.and()
			.logout().logoutUrl("/login/logout").logoutSuccessUrl("/list")
			.deleteCookies("JESSIONID").invalidateHttpSession(true)
			.and()
			.oauth2Login()
				.defaultSuccessUrl("/login/oauth_select")
				.userInfoEndpoint()
				.userService(hotelCustomOAuth2UserService);

		
		
		
		return http.build();
	}
	
	
}
