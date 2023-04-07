package com.boot.hotel.service;

import com.boot.hotel.dto.HotelInfoDTO;

public interface HotelInfoService {

	
	public int maxNum() throws Exception;
	
	public void getReadList(HotelInfoDTO dto) throws Exception;
}
