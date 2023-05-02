package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;

public interface HotelInfoService {

	//호텔 리스트 정보를 불러오는 메소드들
	public List<HotelDTO> getHotelList1(Map<String, Object> params) throws Exception;

    public List<HotelInfoDTO> getHotelList2(Map<String, Object> params) throws Exception;
    
	public List<HotelPictureDTO> getHotelList3(Map<String, Object> params) throws Exception;
	
	//호텔 리스트 갯수를 구하는 메소드
    public int getHotelCount(Map<String, Object> params) throws Exception;
    
    
    //리스트에 표시할 찜 유무를 검색하기 위한 메소드
    public String searchListBasket(Map<String, Object> params) throws Exception;
    
    //카테고리 검색을 위한 전체 갯수 구하기
    public int getHotelCountCategory(Map<String, Object> params) throws Exception;
    
    //카테고리 검색 호텔리스트1
    public List<HotelDTO> getHotelList1Category(Map<String, Object> params) throws Exception;
}

