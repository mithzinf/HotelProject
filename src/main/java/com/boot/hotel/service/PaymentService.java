package com.boot.hotel.service;

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
	

	
	/*
	public void findUser(PaymentDTO dto) throws Exception;
	public void findHotelInfo(PaymentDTO dto) throws Exception;
	public void findHotelPrice(PaymentDTO dto) throws Exception;
	*/
}
