package com.boot.hotel.service;

import com.boot.hotel.dto.MemberDTO;

public interface MemberService {
	
	public int maxNum() throws Exception;
	
	public void insertDataMember(MemberDTO dto) throws Exception;
	
}
