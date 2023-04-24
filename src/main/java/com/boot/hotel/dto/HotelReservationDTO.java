package com.boot.hotel.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HotelReservationDTO {
    
    private Integer  res_num;
    private String userid;
    private String username;
    private int hotel_id;
    private String category;
    private String hotel_name;
    private String room;
    private int people;
    private String status;
    private Date inq_date;
    private String request1;
    private Date check_in;
    private Date check_out;

}
