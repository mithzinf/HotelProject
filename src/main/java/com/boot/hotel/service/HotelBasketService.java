package com.boot.hotel.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.boot.hotel.dto.HotelBasketDTO;

public interface HotelBasketService {
	
	public int maxNum() throws Exception;
	
	public void addHotelBasket(HotelBasketDTO dto) throws Exception; 

	public List<HotelBasketDTO> selectHotelBasket(String userid) throws Exception;

	public void deleteHotelBasket(HotelBasketDTO dto) throws Exception;




}

