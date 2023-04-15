package com.boot.hotel.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HotelInfoDTO {
	
	
	private int hotel_id;
	private int standard;
	private int sweet;
	private int deluxe;
	private String check_in; 
	private String check_out; 
	private String hotel_information;//호텔 소개 글
}
