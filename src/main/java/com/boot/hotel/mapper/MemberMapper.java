package com.boot.hotel.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.boot.hotel.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	
	public int maxNum() throws Exception;
	
	public void insertDataMember(MemberDTO dto) throws Exception;
	
	public void insertDataSocialMember(MemberDTO dto) throws Exception;
	
	public String checkMemberId(String id) throws Exception;
	
	public String checkMemberTel(String tel) throws Exception;
	
	public MemberDTO getReadDataMember(String userid) throws Exception;
	
	public String searchMemberId(Map<String, Object> params) throws Exception;
	
	public String searchMemberPwd(Map<String, Object> params) throws Exception;
	
	public MemberDTO memberLogin(Map<String, Object> params) throws Exception;
	
	public MemberDTO readDataMember(String userid) throws Exception;
	
	public void memberDelete(String userid) throws Exception;
	
}
