package com.boot.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.mapper.HotelDetailMapper;

@Service
public class HotelDetailServiceImpl implements HotelDetailService{

	@Autowired
	private HotelDetailMapper hotelDetailMapper;

	@Override
	public HotelInfoDTO getListsDetailData(int hotel_id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	
	
	
	
	
}
