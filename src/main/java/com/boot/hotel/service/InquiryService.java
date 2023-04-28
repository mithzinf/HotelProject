package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.AnswerBoardDTO;
import com.boot.hotel.dto.HotelInquiryDTO;

public interface InquiryService {
	
	// 게시글
	public int maxInqNum() throws Exception;
	public void insertInquiry(HotelInquiryDTO dto) throws Exception;
	
	public void updateHitCount(HotelInquiryDTO dto) throws Exception;
	
//	public int getListsCount(String searchKey, String searchValue) throws Exception;
	
	public List<HotelInquiryDTO> getInqList(Map<String, Object> params1) throws Exception;
	
	public int getListsCount(Map<String, Object> params1) throws Exception;

	public HotelInquiryDTO getReadData(int num) throws Exception;
	
	public void updateHitCount(int num) throws Exception;
	
	public void updateListData(HotelInquiryDTO dto) throws Exception;
	
	public void deleteData(int num) throws Exception;

	
	
	public void updateAnsweredList(int num) throws Exception;
	

	public void deleteAnswer(int num) throws Exception;
	
	
	
	
	
	// 답변
	public int maxAnsNum() throws Exception;
	public void insertAnswer(AnswerBoardDTO dto) throws Exception;
	
	public List<HotelInquiryDTO> getAnswerList(int num) throws Exception;
	
	
	public int getAnsOnly(int num) throws Exception;
	
	
	
	// 사용자 정보 가져오기
	public List<Map<String, Object>> getUserInfo(String userid) throws Exception;
}
