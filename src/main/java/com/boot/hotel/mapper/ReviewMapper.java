package com.boot.hotel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.boot.hotel.dto.ReviewDTO;
import com.boot.hotel.dto.ReviewScoreDTO;


@Mapper
public interface ReviewMapper {
	
	public int maxNum() throws Exception;
	
	public void insertReviewData(ReviewDTO dto) throws Exception;

	public int getReviewDataCount(int hotel_id);
	
	public List<ReviewDTO> getReadReviewList(Map<String, Object> params);
	//public  ReviewDTO getReadReviewDataList(int review_num);
	
	public int updateReviewData(ReviewDTO dto);
	
	public void deleteReviewData(ReviewDTO dto);

	public Map<String, Object> searchReviewAvg(int hotel_id) throws Exception;
	
}
