package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.HotelReservationDTO;
import com.boot.hotel.dto.PaymentDTO;
import com.boot.hotel.mapper.PaymentMapper;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private PaymentMapper paymentMapper;
	
	// 결제하기
	@Override
	public int maxPayNum() throws Exception {
		return paymentMapper.maxPayNum();
	}
	@Override
	public void insertPayment(PaymentDTO payDto) throws Exception {
		paymentMapper.insertPayment(payDto);
	}
	
	// 예약하기
	@Override
	public int maxResNum() throws Exception {
		return paymentMapper.maxResNum();
	}
	@Override
	public void insertReservation(HotelReservationDTO resDto) throws Exception {
		paymentMapper.insertReservation(resDto);
	}
	
	
	
	@Override
	public List<String> searchHotelTitle(Map<String, Object> params) throws Exception {
		return paymentMapper.searchHotelTitle(params);
	}
	@Override
	public List<Map<String, Object>> getHotelInfo(int hotel_id) throws Exception {
		return paymentMapper.getHotelInfo(hotel_id);
	}
	
	// 사용자 정보
	@Override
	public List<Map<String, Object>> getUserInfo(String userid) throws Exception {
		return paymentMapper.getUserInfo(userid);
	}
	
	
	
}
