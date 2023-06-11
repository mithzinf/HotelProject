package com.boot.hotel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelFacilityDTO;
import com.boot.hotel.dto.HotelFacilityInDTO;
//dao 생성
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
@Mapper
public interface HotelDetailMapper {

	public List<HotelDTO> getHotelById(int hotel_id) throws Exception;
	
	public List<HotelInfoDTO> getHotelInfoById(int hotel_id) throws Exception;
	
	public List<HotelPictureDTO> getHotelPicById(int hotel_id) throws Exception;
	
	public List<HotelFacilityDTO> getHotelFacilityById(int hotel_id) throws Exception;
	
	public List<HotelFacilityInDTO> getHotelFacilityInById(int hotel_id) throws Exception;
	
	//public List<HotelPictureDTO> searchHotelDetail(Map<String, Object> params) throws Exception;
	
	public List<HotelPictureDTO> getTitlePicture(int hotel_id) throws Exception;
	
	public List<HotelPictureDTO> getStandardPicture(int hotel_id) throws Exception;
	
	public List<HotelPictureDTO> getDeluxePicture(int hotel_id) throws Exception;
	
	public List<HotelPictureDTO> getSweetPicture(int hotel_id) throws Exception;
	
	
	//메인 페이지의 리뷰데이터 4개를 최신순으로 불러오는 코드
	public List<Map<String, Object>> getReviewData() throws Exception;
	
	//호텔 날짜 검색 스탠다드
	public int searchDayStandard(Map<String, Object> params) throws Exception;
	
	//호텔 날짜 검색 스위트
	public int searchDaySweet(Map<String, Object> params) throws Exception;
	
	//호텔 날짜 검색 디럭스
	public int searchDayDeluxe(Map<String, Object> params) throws Exception;
		
	
}
