package com.boot.hotel.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.mapper.HotelInfoMapper;

@Service
public class HotelInfoServiceImpl implements HotelInfoService {

    @Autowired
    private HotelInfoMapper hotelInfoMapper;

	@Override
	public List<HotelDTO> getHotelList1(Map<String, Object> params) throws Exception {
		return hotelInfoMapper.getHotelList1(params);
	}

	@Override
	public List<HotelInfoDTO> getHotelList2(Map<String, Object> params) throws Exception {
		return hotelInfoMapper.getHotelList2(params);
	}

	@Override
	public int getHotelCount(Map<String, Object> params) throws Exception {
		return hotelInfoMapper.getHotelCount(params);
	}

//	@Override
//	public List<Map<String, Object>> getHotelList3(params) throws Exception {
//		// TODO Auto-generated method stub
//		return hotelInfoMapper.getHotelList3(params);
//	}

	@Override
	public List<HotelPictureDTO> getHotelList3(Map<String, Object> params) throws Exception {
		return hotelInfoMapper.getHotelList3(params);
	}




}
	





//	@Override
//	public List<HotelDTO> getHotelList1() {
//		return hotelInfoMapper.getHotelList1();
//	}
//
//	@Override
//	public List<HotelInfoDTO> getHotelList2() {
//		return hotelInfoMapper.getHotelList2();
//	}
//
//	@Override
//	public List<HotelPictureDTO> getHotelList3() {
//		return hotelInfoMapper.getHotelList3();
//	}

//	@Override
//	public List<ReviewDTO> getHotelList4() {
//		return hotelInfoMapper.getHotelList4();
//	}
//
//	@Override
//	public List<ReviewScoreDTO> getHotelList5() {
//		return hotelInfoMapper.getHotelList5();
//	}

    

//
//	@Override
//	public Map<String, Object> getHotelList4() {
//		return hotelInfoMapper.getHotelList4();
//	}
//
//	@Override
//	public Map<String, Object> getHotelList5() {
//		return hotelInfoMapper.getHotelList5();
//	}

