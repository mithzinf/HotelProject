package com.boot.hotel.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberDTO {
	
	private int num;
	private String userid;
	private String pwd;
	private String username;
	private String birth;
	private String email1;
	private String email2;
	private String tel1;
	private String tel2;
	private String tel3;
	private int point;
	private String regdate;
	private String picture;
	private String auth;
	
}
