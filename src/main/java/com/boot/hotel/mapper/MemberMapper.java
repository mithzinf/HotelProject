package com.boot.hotel.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.boot.hotel.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	
	public int maxNum() throws Exception;
	
	public void insertDataMember(MemberDTO dto) throws Exception;
}
