package com.boot.hotel.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AnswerBoardDTO {
	
	private int ans_num;
	private String inq_num;
	private String userid;
	private String username;
	private String content;
	private Date created;

}
