package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelFacilityDTO;
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
	public List<HotelFacilityDTO> getHotelFacilityById(int hotel_id) throws Exception {
		return hotelDetailMapper.getHotelFacilityById(hotel_id);
	}

	@Override
	public List<HotelFacilityInDTO> getHotelFacilityInById(int hotel_id) throws Exception {
		// TODO Auto-generated method stub
		return hotelDetailMapper.getHotelFacilityInById(hotel_id);
	}

	@Override
	public List<HotelPictureDTO> getTitlePicture(int hotel_id) throws Exception {
		// TODO Auto-generated method stub
		return hotelDetailMapper.getTitlePicture(hotel_id);
	}

	@Override
	public List<HotelPictureDTO> getStandardPicture(int hotel_id) throws Exception {
		
		return hotelDetailMapper.getStandardPicture(hotel_id);
	}

	@Override
	public List<HotelPictureDTO> getDeluxePicture(int hotel_id) throws Exception {
		// TODO Auto-generated method stub
		return hotelDetailMapper.getDeluxePicture(hotel_id);
	}

	@Override
	public List<HotelPictureDTO> getSweetPicture(int hotel_id) throws Exception {
		// TODO Auto-generated method stub
		return hotelDetailMapper.getSweetPicture(hotel_id);
	}


	//0426 추가 쿼리 : 메인에서 더미 리뷰 데이터 불러오는 쿼리, 나중에 복붙하면 됨
	@Override
	public List<Map<String, Object>> getReviewData() throws Exception {
		
		return hotelDetailMapper.getReviewData();
	}


	@Override
	public int searchDayStandard(Map<String, Object> params) throws Exception {
		return hotelDetailMapper.searchDayStandard(params);
	}

	@Override
	public int searchDaySweet(Map<String, Object> params) throws Exception {
		return hotelDetailMapper.searchDaySweet(params);
	}

	@Override
	public int searchDayDeluxe(Map<String, Object> params) throws Exception {
		return hotelDetailMapper.searchDayDeluxe(params);
	}


	/*
	@Override
	public List<HotelPictureDTO> searchHotelDetail(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return hotelDetailMapper.searchHotelDetail(params);
	}
	 */
	
	

	
	
}
