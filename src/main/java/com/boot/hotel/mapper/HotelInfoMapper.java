package com.boot.hotel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;


@Mapper
public interface HotelInfoMapper {
	
	public List<HotelDTO> getHotelList1(Map<String, Object> params) throws Exception;

	public List<HotelInfoDTO> getHotelList2(Map<String, Object> params) throws Exception;

//	public List<Map<String,Object>> getHotelList3(Map<String, Object> params) throws Exception;
	public List<HotelPictureDTO> getHotelList3(Map<String, Object> params) throws Exception;
	
	public int getHotelCount(Map<String, Object> params) throws Exception;
	
	
	
	
	}




//	public List<ReviewDTO> getHotelList4();
//	public List<ReviewScoreDTO> getHotelList5();


