package com.boot.hotel.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PaymentDTO {
    
    private Integer pay_num;
    private Integer res_num;
    private int hotel_id1;
    private String userid1;
    private String username1;
    private String method;
    private int price;
    private int date_num;
    private int people1;
    

    
}
