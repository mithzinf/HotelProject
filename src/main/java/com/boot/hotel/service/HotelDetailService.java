package com.boot.hotel.service;

import java.util.List;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelFacilityInDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;

public interface HotelDetailService {
	
	public List<HotelDTO> getHotelById(int hotel_id) throws Exception;
	
	public List<HotelInfoDTO> getHotelInfoById(int hotel_id) throws Exception;
	
	public List<HotelPictureDTO> getHotelPicById(int hotel_id) throws Exception;
	
	public List<HotelFacilityInDTO> getHotelFacilityInById(int hotel_id) throws Exception;
}
