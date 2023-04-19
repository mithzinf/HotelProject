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
				.antMatchers("/**","/hotel/**").permitAll()
				.anyRequest().authenticated()
			.and()
			.logout().logoutUrl("/login/logout").logoutSuccessUrl("/")
			.deleteCookies("JESSIONID").invalidateHttpSession(true).logoutSuccessHandler(new RefererLogoutSuccessHandler())
			.and()
			.oauth2Login()
				.defaultSuccessUrl("/login/oauth_select")
				.userInfoEndpoint()
				.userService(hotelCustomOAuth2UserService);

		
		
		
		return http.build();
	}
	
	
	
	//oauth2 로그아웃시 기존 url을 유지하기 위한 클래스
	public class RefererLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	    public RefererLogoutSuccessHandler() {
	        super();
	    }

	    @Override
	    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
	            throws IOException, ServletException {
	        String refererUrl = request.getHeader("Referer");
	        super.setDefaultTargetUrl(refererUrl);
	        super.onLogoutSuccess(request, response, authentication);
	    }
	}
	
	
}
