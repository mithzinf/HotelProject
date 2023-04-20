package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;

public interface HotelInfoService {
	
    public List<HotelDTO> getHotelList1(Map<String, Object> params) throws Exception;

    public List<HotelInfoDTO> getHotelList2(Map<String, Object> params) throws Exception;
    
//	public List<Map<String,Object>> getHotelList3(Map<String, Object> params) throws Exception;
	public List<HotelPictureDTO> getHotelList3(Map<String, Object> params) throws Exception;
	
    public int getHotelCount(Map<String, Object> params) throws Exception;
}

