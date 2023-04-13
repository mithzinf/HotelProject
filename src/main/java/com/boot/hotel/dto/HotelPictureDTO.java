package com.boot.hotel.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HotelPictureDTO {
	
	private int picture_num;
	private String hotel_id;
	private String type;
	private String url;
	
}
