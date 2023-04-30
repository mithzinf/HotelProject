package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import com.boot.hotel.dto.ReviewDTO;
import com.boot.hotel.dto.ReviewScoreDTO;

public interface ReviewService {
	
	public int maxNum() throws Exception;
	
	public void insertReviewData(ReviewDTO dto) throws Exception;

	public int getReviewDataCount(int hotel_id);
	
	public List<ReviewDTO> getReadReviewList(Map<String, Object> params);
	
	public int updateReviewData(ReviewDTO dto);
	
	public void deleteReviewData(ReviewDTO dto);
	
	public Map<String, Object> searchReviewAvg(int hotel_id) throws Exception;

}

