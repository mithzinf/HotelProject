package com.boot.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.MemberDTO;
import com.boot.hotel.mapper.HotelMapper;

@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	private HotelMapper hotelMapper;

	@Override
	public int maxNum() throws Exception {
		return hotelMapper.maxNum();
	}

	@Override
	public void insertDataMember(MemberDTO dto) throws Exception {
		hotelMapper.insertDataMember(dto);
	}
	
	
}
