package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.mapper.HotelImageMapper;

@Service
public class HotelImageServiceImpl implements HotelImageService {

	@Autowired
	private HotelImageMapper hotelImageMapper;
	
	@Override
	public List<String> searchDetailSweet(Map<String, Object> params) throws Exception {
		return hotelImageMapper.searchDetailSweet(params);
	}
	
	
	
}
