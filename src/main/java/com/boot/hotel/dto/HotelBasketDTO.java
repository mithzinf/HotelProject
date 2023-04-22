package com.boot.hotel.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HotelBasketDTO {

	private int basket_num; //찜하기 번호 순서(pk)
	private String userid; 	//찜한 고객fk
	private int hotel_id;	//찜한 호텔fk

}
