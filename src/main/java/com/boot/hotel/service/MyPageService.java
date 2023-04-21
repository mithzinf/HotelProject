package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.MemberDTO;

public interface MyPageService {
	
	//마이페이지 회원정보 띄우기
	public MemberDTO readDataMember(String userid) throws Exception;
	
	//회원 정보 수정
	public void memberUpdate(MemberDTO dto) throws Exception;
	
	//회원 정보 수정 (oauth)
	public void memberUpdateOauth(MemberDTO dto) throws Exception;
	
	//회원 탈퇴
	public void memberDelete(String userid) throws Exception;

	//찜 테이블에 찜한 호텔 찾기
	public List<Map<String, Object>> searchBasket(String userid) throws Exception;
	
	//찜한 호텔의 정보를 찾기
	public List<Map<String, Object>> searchBasketHotel(int hotel_id) throws Exception;
	
	
}
