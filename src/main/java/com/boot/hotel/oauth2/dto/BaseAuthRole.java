package com.boot.hotel.oauth2.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseAuthRole {
	
	GUEST("ROLE_GUEST","손님"), //key,title
	USER("ROLE_USER","일반 사용자");
	
	private final String key;
	private final String title;
	
}
