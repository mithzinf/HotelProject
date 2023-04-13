package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.ReviewDTO;
import com.boot.hotel.dto.ReviewScoreDTO;

public interface HotelInfoService {

<<<<<<< HEAD
	
	public int maxNum() throws Exception;
	
	public int getReadList(HotelInfoDTO dto) throws Exception;
	
	public int getDataHotel(HotelInfoDTO dto) throws Exception;

=======
>>>>>>> 125184fab341ee92a7146d993a93613f4c8d9c2a

    List<HotelDTO> getHotelList1(Map<String, Object> paramMap) throws Exception;

    List<HotelInfoDTO> getHotelList2(Map<String, Object> paramMap) throws Exception;

    List<HotelPictureDTO> getHotelList3(Map<String, Object> paramMap) throws Exception;

    List<ReviewDTO> getHotelList4(Map<String, Object> paramMap) throws Exception;

    List<ReviewScoreDTO> getHotelList5(Map<String, Object> paramMap) throws Exception;

	
}
