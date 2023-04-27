package com.boot.hotel.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.hotel.dto.HotelReservationDTO;
import com.boot.hotel.dto.MemberDTO;
import com.boot.hotel.mapper.MyPageMapper;

@Service
public class MyPageServiceImpl implements MyPageService{

	@Autowired
	private MyPageMapper myPageMapper;
	
	@Override
	public MemberDTO readDataMember(String userid) throws Exception {
		// TODO Auto-generated method stub
		return myPageMapper.readDataMember(userid);
	}

	@Override
	public void memberUpdate(MemberDTO dto) throws Exception {
		myPageMapper.memberUpdate(dto);
	}

	@Override
	public void memberUpdateOauth(MemberDTO dto) throws Exception {
		myPageMapper.memberUpdateOauth(dto);
	}

	@Override
	public void memberDelete(String userid) throws Exception {
		myPageMapper.memberDelete(userid);
	}

	@Override
	public List<Map<String, Object>> searchBasket(String userid) throws Exception {
		return myPageMapper.searchBasket(userid);
	}

	@Override
	public List<Map<String, Object>> searchBasketHotel(int hotel_id) throws Exception {
		return myPageMapper.searchBasketHotel(hotel_id);
	}

	@Override
	public void basketDelete(int basket_num) throws Exception {
		myPageMapper.basketDelete(basket_num);
	}

	@Override
	public int reservationMaxNum(String userid) throws Exception {
		return myPageMapper.reservationMaxNum(userid);
	}

	@Override
	public List<Map<String, Object>> getMyReservationLists(Map<String, Object> params) throws Exception {
		return myPageMapper.getMyReservationLists(params);
	}

	@Override
	public List<Map<String, Object>> getMyReservationListsAdd(Map<String, Object> params) throws Exception {
		return myPageMapper.getMyReservationListsAdd(params);
	}

	@Override
	public void deletePay(int res_num) throws Exception {
		myPageMapper.deletePay(res_num);
	}

	@Override
	public void deleteReservation(int res_num) throws Exception {
		myPageMapper.deleteReservation(res_num);
	}




	
	
	

	
	
}
