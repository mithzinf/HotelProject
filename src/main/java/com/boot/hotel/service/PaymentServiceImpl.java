package com.boot.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void createReservation(HotelReservationDTO resDto) throws Exception {
		paymentMapper.createReservation(resDto);
	}
	

	
	
	

	

	
	
	
/*
	@Override
	public void findUser(PaymentDTO dto) throws Exception {
		paymentMapper.findUser(dto);
	}

	@Override
	public void findHotelInfo(PaymentDTO dto) throws Exception {
		paymentMapper.findHotelInfo(dto);
	}

	@Override
	public void findHotelPrice(PaymentDTO dto) throws Exception {
		paymentMapper.findHotelPrice(dto);
	}
*/



	
	
	
}
