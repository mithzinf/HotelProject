package com.boot.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.MemberDTO;
import com.boot.hotel.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public int maxNum() throws Exception {
		return memberMapper.maxNum();
	}

	@Override
	public void insertDataMember(MemberDTO dto) throws Exception {
		memberMapper.insertDataMember(dto);
	}
	
	
}
