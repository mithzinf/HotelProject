package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.AnswerBoardDTO;
import com.boot.hotel.dto.HotelInquiryDTO;
import com.boot.hotel.mapper.InquiryMapper;

@Service
public class InquiryServiceImpl implements InquiryService{
	
	// 게시글
	@Autowired
	private InquiryMapper inquiryMapper;

	@Override
	public int maxInqNum() throws Exception {
		return inquiryMapper.maxInqNum();
	}

	@Override
	public void insertInquiry(HotelInquiryDTO dto) throws Exception {
		inquiryMapper.insertInquiry(dto);
	}

	@Override
	public void updateHitCount(HotelInquiryDTO dto) throws Exception {
		inquiryMapper.updateHitCount(dto);
	}

	@Override
	public int getListsCount(Map<String, Object> params1) throws Exception {
		return inquiryMapper.getListsCount(params1);
	}

	@Override
	public List<HotelInquiryDTO> getInqList(Map<String, Object> params1) throws Exception {
		return inquiryMapper.getInqList(params1);
	}

	@Override
	public HotelInquiryDTO getReadData(int num) throws Exception {
		return inquiryMapper.getReadData(num);
	}

	@Override
	public void updateHitCount(int num) throws Exception {
		inquiryMapper.updateHitCount(num);
	}

	@Override
	public void updateListData(HotelInquiryDTO dto) throws Exception {
		inquiryMapper.updateListData(dto);
	}

	@Override
	public void deleteData(int num) throws Exception {
		inquiryMapper.deleteData(num);
	}
	
	
	
	
	@Override
	public void updateAnsweredList(int num) throws Exception {
		inquiryMapper.updateAnsweredList(num);
	}
	
	
	
	

	// 답변
	@Override
	public int maxAnsNum() throws Exception {
		return inquiryMapper.maxAnsNum();
	}

	@Override
	public void insertAnswer(AnswerBoardDTO dto) throws Exception {
		inquiryMapper.insertAnswer(dto);
	}

	@Override
	public List<HotelInquiryDTO> getAnswerList(int num) throws Exception {
		return inquiryMapper.getAnswerList(num);
	}



	@Override
	public void deleteAnswer(int num) throws Exception {
		inquiryMapper.deleteData(num);		
	}

	
	
	
	@Override
	public int getAnsOnly(int num) throws Exception {
		return inquiryMapper.getAnsOnly(num);
	}

	
	


	// 사용자 정보
	@Override
	public List<Map<String, Object>> getUserInfo(String userid) throws Exception {
		return inquiryMapper.getUserInfo(userid);
	}



	



	
	
	
	
}
