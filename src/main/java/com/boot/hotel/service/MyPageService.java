package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.HotelReservationDTO;
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
	
	//찜한 호텔 삭제
	public void basketDelete(int basket_num) throws Exception;
	
	//내 예약 개수 구하기
	public int reservationMaxNum(String userid) throws Exception;
	
	//내 예약 정보 불러오기
	public List<Map<String, Object>> getMyReservationLists(Map<String, Object> params) throws Exception; 
	
	//내 예약 정보 불러오기 추가
	public List<Map<String, Object>> getMyReservationListsAdd(Map<String, Object> params) throws Exception;
	
	//결제 테이블 삭제
	public void deletePay(int res_num) throws Exception;
	
	//예약 테이블 삭제
	public void deleteReservation(int res_num) throws Exception;
		
	
}
