package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.ReviewDTO;
import com.boot.hotel.dto.ReviewScoreDTO;

public interface ReviewService {
	
	//리뷰 테이블 일련번호 최대값 구하기
	public int maxNum() throws Exception;
	
	//리뷰 작성 처리
	public void insertReviewData(ReviewDTO dto) throws Exception;

	//리뷰 테이블 갯수 구하기
	public int getReviewDataCount(int hotel_id);
	
	//리뷰 페이징 리스트 정보 구하기
	public List<ReviewDTO> getReadReviewList(Map<String, Object> params);
	
	//리뷰 수정 메소드
	public int updateReviewData(ReviewDTO dto);
	
	//리뷰 삭제 메소드
	public void deleteReviewData(ReviewDTO dto);
	
	//리뷰 평점 구하는 메소드
	public Map<String, Object> searchReviewAvg(int hotel_id) throws Exception;

}

