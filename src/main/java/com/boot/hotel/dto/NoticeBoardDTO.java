package com.boot.hotel.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NoticeBoardDTO {

	//공지사항 게시판 DTO - 어진
	private int notice_num;
	private String userid;
	private String username;
	private String subject;
	private String context;
	private int hitCount;
	private Date created;
}
