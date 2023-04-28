package com.boot.hotel.dto;

import java.sql.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReviewDTO {
	
	
	private int hotel_id;
	private int review_num;
	private int score;
	private String userid;
	private Date created;
	private String context;
	private String username;

	
}