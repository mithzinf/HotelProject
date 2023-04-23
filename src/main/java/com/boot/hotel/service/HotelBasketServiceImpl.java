package com.boot.hotel.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.HotelBasketDTO;
import com.boot.hotel.mapper.HotelBasketMapper;


@Service
public class HotelBasketServiceImpl implements HotelBasketService {
	
	@Autowired
	private HotelBasketMapper hotelBasketMapper;

	@Override
	public int maxNum() throws Exception {
		return hotelBasketMapper.maxNum();
	}
	
	@Override
	public void addHotelBasket(HotelBasketDTO dto) throws Exception {
		hotelBasketMapper.addHotelBasket(dto);
	}

	@Override
	public List<HotelBasketDTO> selectHotelBasket(String userid) throws Exception {
		return hotelBasketMapper.selectHotelBasket(userid);
	}

	@Override
	public void deleteHotelBasket(HotelBasketDTO dto) throws Exception {
		hotelBasketMapper.deleteHotelBasket(dto);
		
	}

	
	
	
	
	
	
	
}
	

