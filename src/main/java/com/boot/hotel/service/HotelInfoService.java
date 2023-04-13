package com.boot.hotel.service;

import com.boot.hotel.dto.HotelInfoDTO;

public interface HotelInfoService {

	
	public int maxNum() throws Exception;
	
	public int getReadList(HotelInfoDTO dto) throws Exception;
	
	public int getDataHotel(HotelInfoDTO dto) throws Exception;


}
