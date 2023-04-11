package com.boot.hotel.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.boot.hotel.dto.HotelInfoDTO;

@Mapper
public interface HotelInfoMapper {

	
	public int maxNum() throws Exception;
	
	public int getReadList(HotelInfoDTO dto) throws Exception;
	
	public int getDataHotel(HotelInfoDTO dto) throws Exception;
}
