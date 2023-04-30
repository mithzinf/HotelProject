package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.ReviewDTO;
import com.boot.hotel.dto.ReviewScoreDTO;
import com.boot.hotel.mapper.ReviewMapper;
@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	
	@Override
	public int maxNum() throws Exception {
		return reviewMapper.maxNum();
	}

	@Override
	public void insertReviewData(ReviewDTO dto) throws Exception {
		reviewMapper.insertReviewData(dto);		
	}

	@Override
	public int getReviewDataCount(int hotel_id) {
		return reviewMapper.getReviewDataCount(hotel_id);
	}

	@Override
	public int updateReviewData(ReviewDTO dto) {
		return reviewMapper.updateReviewData(dto);
	}

	@Override
	public void deleteReviewData(ReviewDTO dto) {
		reviewMapper.deleteReviewData(dto);
	}

	@Override
	public List<ReviewDTO> getReadReviewList(Map<String, Object> params) {
		return reviewMapper.getReadReviewList(params);
	}

	@Override
	public Map<String, Object> searchReviewAvg(int hotel_id) throws Exception {
		return reviewMapper.searchReviewAvg(hotel_id);
	}



}
