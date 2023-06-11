package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.AnswerBoardDTO;
import com.boot.hotel.dto.HotelInquiryDTO;

public interface InquiryService {
	
	
	// 게시글
	// 최근 게시글 조회
	public int maxInqNum() throws Exception;
	
	// 문의 게시글 작성
	public void insertInquiry(HotelInquiryDTO dto) throws Exception;
	
	// 게시글 조회
	public void updateHitCount(HotelInquiryDTO dto) throws Exception;
	
	
	// 게시글 전체 갯수 받아오기
	public int getListsCount(Map<String, Object> params1) throws Exception;
	
	// 게시글 조회
	public List<HotelInquiryDTO> getInqList(Map<String, Object> params1) throws Exception;
	
	// 게시글 내용 가져오기
	public HotelInquiryDTO getReadData(int num) throws Exception;
	
	// 조회수 증가
	public void updateHitCount(int num) throws Exception;
	
	// 게시글 수정하기
	public void updateListData(HotelInquiryDTO dto) throws Exception;
	
	// 게시글 삭제하기
	public void deleteData(int num) throws Exception;
	
	// 답변 유무 확인하기 - 답변 없으면 0 있으면 
	public void updateAnsweredList(int num) throws Exception;
	
	
	
	
	
	
	// 답변
	// 최근 답변 조회
	public int maxAnsNum() throws Exception;
	
	// 답변 달기
	public void insertAnswer(AnswerBoardDTO dto) throws Exception;
	
	// 전체 답변 정보 가져오기
	public List<HotelInquiryDTO> getAnswerList(int num) throws Exception;
	
	// 답변 삭제하기
	public void deleteAnswer(int num) throws Exception;
	
	// 답변 데이터 가져오기
	public int getAnsOnly(int num) throws Exception;
	
	
	
	
	// 사용자 정보 가져오기
	public List<Map<String, Object>> getUserInfo(String userid) throws Exception;
}
