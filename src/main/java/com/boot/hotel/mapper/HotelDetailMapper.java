package com.boot.hotel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
//dao 생성
import com.boot.hotel.dto.HotelInfoDTO;
@Mapper
public interface HotelDetailMapper {

	public List<HotelInfoDTO> getListsDetailData() throws Exception;
	
	
//	public HotelInfoDTO getReadData(int hotel_id) throws Exception;
}
