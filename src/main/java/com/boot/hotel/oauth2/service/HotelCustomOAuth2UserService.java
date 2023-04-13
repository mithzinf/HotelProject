package com.boot.hotel.oauth2.service;

import java.util.Collections;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.boot.hotel.oauth2.dto.BaseAuthUser;
import com.boot.hotel.oauth2.dto.OAuthAttributes;
import com.boot.hotel.oauth2.dto.SessionUser;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class HotelCustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

	@Autowired
	private final HttpSession httpSession;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest)
			throws OAuth2AuthenticationException {
		System.out.println("서비스 진입 성공!!");
		//OAuth2로 로그인한 정보를 받아줄 객체를 생성해 데이터를 받아오고
		//OAuth2User로 로그인 정보를 저장
		OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthUserService = 
				new DefaultOAuth2UserService();
			OAuth2User oauth2User = oauthUserService.loadUser(userRequest);
				
				//간편 로그인을 진행하는 플랫폼 코드(google,kakao,naver...)를 저장
				String registrationId = userRequest.getClientRegistration().
						getRegistrationId();
				
				System.out.println(registrationId); //google
				
				//OAuth2 로그인 진행시 key가 되는 필드값(Primary key역할)을 저장
				//구글:sub, 네이버:response, 카카오:id 셋중 하나
				String userNameAttributeName =
						userRequest.getClientRegistration().getProviderDetails().
						getUserInfoEndpoint().getUserNameAttributeName();
				
				System.out.println(userNameAttributeName); //sub,response,id중 하나 출력
				
				//OAuthAttributes 파일을 생성
				//로그인을 통해 가져온 Oauth2User의 속성을 담아두는 of메소드를 이용해
				//요청한 플랫폼에 맞는 로그인 데이터를 json형태로 반환해 저장
				//실질적인 OAuth2 플랫폼에 맞는 json데이터를 만들어주는 곳!!
				OAuthAttributes attributes = 
						OAuthAttributes.of(registrationId, userNameAttributeName,
								oauth2User.getAttributes());//google,sub,로그인정보
				
				System.out.println(attributes.getAttributes());//json형태의 데이터
				
				
				// 세션에 소셜 로그인 데이터 이메일, 이름을 저장후 세션을 올림
				// 소셜 로그인시 id는 소셜 로그인 이메일, 이름은 이름으로 설정 (혼동주의)
				// 따라서 소셜 로그인의 아이디는 이메일 형식임
				SessionUser sessionUser = new SessionUser(attributes.getEmail(), attributes.getName());
		        httpSession.setAttribute("sessionUser", sessionUser);

		        // 로그인 데이터를 반환하면서 Security 인증까지 같이 해준다.
		        return new DefaultOAuth2User(Collections.singleton(
		                new SimpleGrantedAuthority("ROLE_USER")), 
		                attributes.getAttributes(),
		                attributes.getNameAttributeKey());
		
		
	}
	
}

