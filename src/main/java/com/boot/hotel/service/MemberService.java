package com.boot.hotel.service;

import java.util.Map;

import com.boot.hotel.dto.MemberDTO;

public interface MemberService {
	
	//member테이블의 전체 회원수 구하기
	public int maxNum() throws Exception;
	
	//일반 로그인 회원가입 처리
	public void insertDataMember(MemberDTO dto) throws Exception;
	
	//소셜 로그인 회원가입 처리
	public void insertDataSocialMember(MemberDTO dto) throws Exception;
	
	//아이디 중복체크 처리
	public String checkMemberId(String id) throws Exception;
	
	//전화번호 중복체크 처리
	public String checkMemberTel(String tel) throws Exception;
	
	//전화전호 중복체크 처리 (회원 정보 수정)
	public String checkMemberTelUpdate(Map<String, Object> params) throws Exception;
	
	//소셜 로그인 버튼 클릭시 로그인인지 회원가입인지 판별하기 위한 데이터 출력
	public MemberDTO getReadDataMember(String userid) throws Exception;
	
	//아이디 찾기
	public String searchMemberId(Map<String, Object> params) throws Exception;
	
	//비밀번호 찾기
	public String searchMemberPwd(Map<String, Object> params) throws Exception;
	
	//일반 로그인 처리를 위해
	public MemberDTO memberLogin(Map<String, Object> params) throws Exception;
	
}
