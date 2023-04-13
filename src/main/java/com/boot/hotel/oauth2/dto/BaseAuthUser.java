package com.boot.hotel.oauth2.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor //파라미터가 없는 기본 생성자를 생성
@Entity
public class BaseAuthUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String email;
	
	
	//enum entity를 매핑할때 사용, ORDINAL:순서값(1,2,4 등..) / STRING : 문자열 자체를 저장
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BaseAuthRole role;
	
	//생성자
	@Builder
	public BaseAuthUser(String name,String email,BaseAuthRole role) {
		System.out.println("베이스오스유저 진입!!");
		System.out.println("이름:" + name);
		this.name = name;
		this.email = email;
		this.role = role;
	}
	

	//프로필 정보가 수정되면 자동으로 적용
	public BaseAuthUser update(String name) {
		this.name = name;
		return this;
	}
	
	public String getRoleKey() {
		return this.role.getKey();
	}
	
	
}
