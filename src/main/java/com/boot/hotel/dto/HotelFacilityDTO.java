package com.boot.hotel.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HotelFacilityDTO {
	
	
	private int hotel_id;
	private int info24;
	private int free_parking;
	private int free_wifi;
	private int no_smoking;
	private int breakfast;
	private int fitness;
	private int restaurant; 
	private int cafe;
	private int convenience;
	
}
