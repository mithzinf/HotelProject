package com.boot.hotel.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HotelInfoDTO {
	
	
	private int hotel_id;
	private String hotel_name;
	private String category;
	private String addr1;
	private String addr2;
	private String tel;
	private String latitude; 
	private String longitude;
	private int like_num;

	private int standard;
	private int sweet;
	private int deluxe;
	private int check_in; 
	private int check_out; 
	private int hotel_info;//호텔 소개 글


	private int picture_num;
	private String type;
	private String url;

	
	private int info24;
	private int free_parking;
	private int free_wifi;
	private int no_smoking;
	private int breakfast;
	private int fitness;
	private int restaurant; 
	private int cafe;
	private int convenience;
	
	private int toiletries;
	private int shower;
	private int towel;
	private int tv;
	private int coffeemaker; 
	private int air_conditional;
	private int slipper;
	private int heating;
	private int table1;
	private int pc;
	private int count;
	private int ave;
	 
}
