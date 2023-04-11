package com.boot.hotel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
//dao 생성
import com.boot.hotel.dto.HotelInfoDTO;
@Mapper
public interface HotelDetailMapper {

	public HotelInfoDTO getListsDetailData(int hotel_id) throws Exception;
	//boradMapper의 getReadData(int num)보고 따라한건데요..한 호텔당 보여줄 객실..ㅠ
}
