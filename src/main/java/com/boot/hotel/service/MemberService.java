package com.boot.hotel.service;

import java.util.Map;

import com.boot.hotel.dto.MemberDTO;

public interface MemberService {
	
	public int maxNum() throws Exception;
	
	public void insertDataMember(MemberDTO dto) throws Exception;
	
	public String checkMemberId(String id) throws Exception;
	
	public String checkMemberTel(String tel) throws Exception;
	
	public MemberDTO getReadDataMember(String userid) throws Exception;
	
	public String searchMemberId(Map<String, Object> params) throws Exception;
	
	public String searchMemberPwd(Map<String, Object> params) throws Exception;
}
