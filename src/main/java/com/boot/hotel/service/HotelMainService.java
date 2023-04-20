package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

public interface HotelMainService {

	public List<Map<String, Object>> getHotelMain(int hotel_id) throws Exception;
	
	public List<Map<String, Object>> searchMainHotelTest(Map<String, Object> params) throws Exception;
	
}
