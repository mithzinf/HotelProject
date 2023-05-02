package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.HotelReservationDTO;
import com.boot.hotel.dto.PaymentDTO;

public interface PaymentService {
	
	// 최신 결제 내역 조회
	public int maxPayNum() throws Exception;
	
	// 결제완료시 결제 내역 저장
	public void insertPayment(PaymentDTO payDto) throws Exception;
	
	// 최신 예약 정보 조회
	public int maxResNum() throws Exception;
	
	// 예매/결제 시 예약 정보 저장
	public void insertReservation(HotelReservationDTO resDto) throws Exception;
	
	// 전경 사진 가져오기
	public List<String> searchHotelTitle(Map<String, Object> params) throws Exception;
	
	// 호텔 정보 가져오기
	public List<Map<String, Object>> getHotelInfo(int hotel_id) throws Exception;
	
	// 사용자 정보 가져오기
	public List<Map<String, Object>> getUserInfo(String userid) throws Exception;
}
