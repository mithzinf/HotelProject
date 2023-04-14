package com.boot.hotel.oauth2.dto;

import java.util.Map;


import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private Map<String, Object> attributes;

	private String nameAttributeKey;
	private String name;
	private String email;
	
	//생성자
	@Builder
	public OAuthAttributes(Map<String,Object> attributes,
			String nameAttributeKey,String name,String email) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
	}
	
	
	//ofGoogle 메소드에서 변환작업을 함
	public static OAuthAttributes of(String registrationId,String userNameAttributeName,
			Map<String, Object> attributes) {
		
		//이곳에서 구글,카카오,네이버 등을 구분(ofGoogle,ofNaver,ofKakao)
		if(registrationId.equals("kakao")) {//id
			return ofKakao(userNameAttributeName,attributes);
		}//userNameAttributeName에 id가 들어온다
				
		if(registrationId.equals("naver")) {//response
			return ofNaver("id",attributes);
		}
		
		
		return ofGoogle(userNameAttributeName,attributes);
	
	
		
	}
	


	//Oauth2User에서 반환하는 사용자 정보는 Map형태이기 때문에
	//Google
	private static OAuthAttributes ofGoogle(String userNameAttributeName,
			Map<String, Object> attributes) {
		
		//받아온 데이터를 읽어서 반환
		return OAuthAttributes.builder()
				.name((String)attributes.get("name"))
				.email((String)attributes.get("email"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	
	//Kakao
	private static OAuthAttributes ofKakao(String userNameAttributeName,
			Map<String, Object> attributes) {
		
		//kakao_account 사용자 정보(email)가 있음
		Map<String,Object> kakaoAccount =
				(Map<String, Object>)attributes.get("kakao_account");
		
		//kakao_profile안에 profile이라는 json객체가 있음
		Map<String, Object> kakaoProfile = 
				(Map<String,Object>)kakaoAccount.get("profile");
		
		return OAuthAttributes.builder()
				.name((String)kakaoProfile.get("nickname"))
				.email((String)kakaoAccount.get("email"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	//Naver
	private static OAuthAttributes ofNaver(String userNameAttributeName,
			Map<String, Object> attributes) {

		//kakao_account에 사용자 정보(email)가 있음
		Map<String, Object> response = 
				(Map<String, Object>)attributes.get("response");
				
		return OAuthAttributes.builder()
				.name((String)response.get("name"))
				.email((String)response.get("email"))
				.attributes(response)
				.nameAttributeKey(userNameAttributeName)
				.build();		
		
	}
	

	
}
