package com.boot.hotel.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HotelDTO {
	
	
	private int hotel_id;
	private String hotel_name;
	private String category;
	private String addr1;
	private String addr2;
	private String tel;
	private String latitude; 
	private String longitude;
	private int like_num;
	
	//잔여객실 체크를 위한 변수
	private String soldout;
	private int num;
	
	//찜 유무 표시를 위한 변수
	private Boolean basket_;
	
	//리뷰 평점,개수 표시를 위한 변수
	private double avg;
	private int count;
	
}