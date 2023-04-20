package com.boot.hotel.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.MemberDTO;
import com.boot.hotel.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper memberMapper;
	
	//회원번호를 입력하기 위한 기존 회원번호 최고숫자 찾기 메소드
	@Override
	public int maxNum() throws Exception {
		return memberMapper.maxNum();
	}

	//회원가입 처리 메소드
	@Override
	public void insertDataMember(MemberDTO dto) throws Exception {
		memberMapper.insertDataMember(dto);
	}
	
	//소셜회원가입 처리 메소드
	@Override
	public void insertDataSocialMember(MemberDTO dto) throws Exception {
		memberMapper.insertDataSocialMember(dto);
		
	}

	//아이디 중복체크 메소드
	@Override
	public String checkMemberId(String id) throws Exception {
		return memberMapper.checkMemberId(id);
	}

	@Override
	public String checkMemberTel(String tel) throws Exception {
		return memberMapper.checkMemberTel(tel);
	}

	@Override
	public MemberDTO getReadDataMember(String userid) throws Exception {
		return memberMapper.getReadDataMember(userid);
	}

	@Override
	public String searchMemberId(Map<String, Object> params) throws Exception {
		return memberMapper.searchMemberId(params);
	}

	@Override
	public String searchMemberPwd(Map<String, Object> params) throws Exception {
		return memberMapper.searchMemberPwd(params);
	}

	@Override
	public MemberDTO memberLogin(Map<String, Object> params) throws Exception {
		return memberMapper.memberLogin(params);
	}

	@Override
	public MemberDTO readDataMember(String userid) throws Exception {
		return memberMapper.getReadDataMember(userid);
	}
	
	@Override
	public void memberDelete(String userid) throws Exception {
		memberMapper.memberDelete(userid);
	}

	

	








	
}
