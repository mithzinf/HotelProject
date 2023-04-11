package com.boot.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.mapper.HotelInfoMapper;


@Service
public class HotelInfoServiceImpl implements HotelInfoService {
	
	@Autowired
	private HotelInfoMapper hotelInfoMapper;
	
	@Override
	public int maxNum() throws Exception {	
		return hotelInfoMapper.maxNum();
	}

	@Override
	public int getReadList(HotelInfoDTO dto) throws Exception {
		return hotelInfoMapper.getReadList(dto);
	}

	@Override
	public int getDataHotel(HotelInfoDTO dto) throws Exception {
		return hotelInfoMapper.getDataHotel(dto);
	}

	
}
