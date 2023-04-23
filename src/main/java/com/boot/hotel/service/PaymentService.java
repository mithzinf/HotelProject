package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.HotelReservationDTO;
import com.boot.hotel.dto.PaymentDTO;

public interface PaymentService {
	
	// 결제 내역
	public int maxPayNum() throws Exception;
	public void insertPayment(PaymentDTO payDto) throws Exception;
	
	// 예약 내역
	public int maxResNum() throws Exception;
	public void createReservation(HotelReservationDTO resDto) throws Exception;
	
	public List<String> searchHotelTitle(Map<String, Object> params) throws Exception;
	
	// 호텔 정보 가져오기
	public List<Map<String, Object>> getHotelInfo(int hotel_id) throws Exception;
	
	// 사용자 정보 가져오기
	public List<Map<String, Object>> getUserInfo(String userid) throws Exception;
}
