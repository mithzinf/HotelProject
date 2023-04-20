package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelFacilityInDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.mapper.HotelDetailMapper;

@Service
public class HotelDetailServiceImpl implements HotelDetailService{

	@Autowired
	private HotelDetailMapper hotelDetailMapper;

	@Override
	public List<HotelDTO> getHotelById(int hotel_id) throws Exception {
		// TODO Auto-generated method stub
		return hotelDetailMapper.getHotelById(hotel_id);
	}

	@Override
	public List<HotelInfoDTO> getHotelInfoById(int hotel_id) throws Exception {
		// TODO Auto-generated method stub
		return hotelDetailMapper.getHotelInfoById(hotel_id);
	}

	@Override
	public List<HotelPictureDTO> getHotelPicById(int hotel_id) throws Exception {
		// TODO Auto-generated method stub
		return hotelDetailMapper.getHotelPicById(hotel_id);
	}

	@Override
	public List<HotelFacilityInDTO> getHotelFacilityInById(int hotel_id) throws Exception {
		// TODO Auto-generated method stub
		return hotelDetailMapper.getHotelFacilityInById(hotel_id);
	}

	@Override
	public List<Map<String, Object>> searchHotelDetail(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return hotelDetailMapper.searchHotelDetail(params);
	}


	



	

	

	
	
}
