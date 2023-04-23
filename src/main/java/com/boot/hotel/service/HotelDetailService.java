package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelFacilityInDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;

public interface HotelDetailService {
	
	public List<HotelDTO> getHotelById(int hotel_id) throws Exception;
	
	public List<HotelInfoDTO> getHotelInfoById(int hotel_id) throws Exception;
	
	public List<HotelPictureDTO> getHotelPicById(int hotel_id) throws Exception;
	
	public List<HotelFacilityInDTO> getHotelFacilityInById(int hotel_id) throws Exception;
	
	public List<HotelPictureDTO> searchHotelDetail(Map<String, Object> params) throws Exception;
	
	//List<String>이 뭘까? : List<String>은 HotelDetailService 인터페이스에서 'searchHotelDetail() 메서드가 반환하는 값의 타입이 
	//List<String>이라는 것...... String값들을 담은 리스트를 반환하는 것을 의미한다...
	
	//hotelDetailMapper.xml에서는 url 컬럼값만 셀렉해오므로, HotelImageService인터페이스에서는 List<String>형태로 반환값을 정의한 것
	//이렇게 함으로써 searchHotelDetail()메서드가 hotel_id와 type(객실타입)에 해당하는 호텔 사진 이름(url)을 반환하는 것을 나타낼 수 있다
	
	
	//내가 궁금했던 거 : 왜 List<String>으로 받는지! 왜냐하면 반환값으로 여러개의 사진이 올 수도 있으니까~
	
	
	
}
