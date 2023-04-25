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
	
	//리스트에 표시할 찜 유무를 검색하기 위한 메소드
    public String searchListBasket(Map<String, Object> params) throws Exception;
    
    //카테고리 검색을 위한 전체 갯수 구하기
    public int getHotelCountCategory(Map<String, Object> params) throws Exception;
    
    //카테고리 검색 호텔리스트1
    public List<HotelDTO> getHotelList1Category(Map<String, Object> params) throws Exception;
	
}




//	public List<ReviewDTO> getHotelList4();
//	public List<ReviewScoreDTO> getHotelList5();


