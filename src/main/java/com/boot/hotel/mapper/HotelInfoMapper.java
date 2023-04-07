package com.boot.hotel.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.boot.hotel.dto.HotelInfoDTO;

@Mapper
public interface HotelInfoMapper {

	
	public int maxNum() throws Exception;
	
	public void getReadList(HotelInfoDTO dto) throws Exception;
	
}
