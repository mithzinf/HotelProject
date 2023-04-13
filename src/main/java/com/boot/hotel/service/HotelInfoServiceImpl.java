package com.boot.hotel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.ReviewDTO;
import com.boot.hotel.dto.ReviewScoreDTO;
import com.boot.hotel.mapper.HotelInfoMapper;

@Service
public class HotelInfoServiceImpl implements HotelInfoService {

    @Autowired
    private HotelInfoMapper hotelInfoMapper;

	@Override
	public List<HotelDTO> getHotelList1(Map<String, Object> paramMap) throws Exception {
		return null;
	}

	@Override
	public List<HotelInfoDTO> getHotelList2(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HotelPictureDTO> getHotelList3(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewDTO> getHotelList4(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewScoreDTO> getHotelList5(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}