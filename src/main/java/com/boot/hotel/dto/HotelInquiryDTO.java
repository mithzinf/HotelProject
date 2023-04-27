package com.boot.hotel.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HotelInquiryDTO {
	
	private int inq_num;
    private String userid;
    private String username;
    private String tel;
    private String email;
    private String category;
    private String inq_cate;
    private String subject;
    private String context;
    private String hitCount;
    private Date created;
    private int answer;
	
	
	
}
