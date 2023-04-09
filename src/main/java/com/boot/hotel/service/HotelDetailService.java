package com.boot.hotel.service;

import java.util.List;

import com.boot.hotel.dto.HotelInfoDTO;

public interface HotelDetailService {
	
	//호텔 객실 리스트 처리
	public List<HotelInfoDTO> getListsDetailData() throws Exception;
	
	//특정 호텔의 객실 정보 불러오기
	//public HotelInfoDTO getReadData(int hotel_id) throws Exception;

}
