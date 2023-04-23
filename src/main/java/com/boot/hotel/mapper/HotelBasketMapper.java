package com.boot.hotel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.boot.hotel.dto.HotelBasketDTO;
import com.boot.hotel.dto.MemberDTO;


@Mapper
public interface HotelBasketMapper {
	
	public int maxNum() throws Exception;
	
	public void addHotelBasket(HotelBasketDTO dto) throws Exception; 

	public List<HotelBasketDTO> selectHotelBasket(String userid) throws Exception;

	public void deleteHotelBasket(HotelBasketDTO dto) throws Exception;



}

