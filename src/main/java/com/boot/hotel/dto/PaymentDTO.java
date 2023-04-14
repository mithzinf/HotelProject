package com.boot.hotel.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PaymentDTO {
	
    private int pay_num;
    private int res_num;
    private int hotel_id1;
    private String userid1;
    private String username1;
    private String method;
    private int price;
    private int date_num;
    private int people1;

}
