package com.boot.hotel.oauth2.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

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
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated()
			.and()
			.logout().logoutUrl("/logout").logoutSuccessUrl("/login/logout")
			.deleteCookies("JESSIONID").invalidateHttpSession(false)
			.and()
			.oauth2Login()
				.defaultSuccessUrl("/login/oauth_select")
				.userInfoEndpoint()
				.userService(hotelCustomOAuth2UserService);

		
		
		
		return http.build();
	}
	
	
	
	
	
}
