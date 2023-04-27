package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.mapper.HotelMainMapper;
import com.boot.hotel.mapper.MemberMapper;

@Service
public class HotelMainServiceImpl implements HotelMainService{
	
	@Autowired
	private HotelMainMapper hotelMainMapper;

	@Override
	public List<Map<String, Object>> getHotelMain(int hotel_id) throws Exception {
		return hotelMainMapper.getHotelMain(hotel_id);
	}

	@Override
	public List<Map<String, Object>> searchMainHotel(Map<String, Object> params) throws Exception {
		return hotelMainMapper.searchMainHotel(params);
	}

	@Override
	public int searchDay(Map<String, Object> params) throws Exception {
		return hotelMainMapper.searchDay(params);
	}
	
	
	

}
