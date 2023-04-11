package com.boot.hotel.dto;



import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReviewScoreDTO {
	
	private int hotel_id;
	private int count;
	private int ave;
	
}