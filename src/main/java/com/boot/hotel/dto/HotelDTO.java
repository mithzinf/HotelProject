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
	
}