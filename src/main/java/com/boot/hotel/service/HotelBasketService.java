package com.boot.hotel.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.boot.hotel.dto.HotelBasketDTO;

public interface HotelBasketService {
	
	//찜 목록 테이블의 일련 번호 최대값 구하기
	public int maxNum() throws Exception;
	
	//찜 추가
	public void addHotelBasket(HotelBasketDTO dto) throws Exception; 

	//찜 목록 리스트
	public List<HotelBasketDTO> selectHotelBasket(String userid) throws Exception;

	//찜 삭제
	public void deleteHotelBasket(HotelBasketDTO dto) throws Exception;

}

