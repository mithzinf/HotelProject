package com.boot.hotel.oauth2.dto;

import java.io.Serializable;

import lombok.Getter;

//인증된 사용자 정보를 DTD에 넣고 세션에 저장
@Getter
public class SessionUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	
	public SessionUser(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	
}
