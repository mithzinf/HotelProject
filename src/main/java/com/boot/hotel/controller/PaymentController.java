package com.boot.hotel.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.text.DateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelReservationDTO;
import com.boot.hotel.dto.PaymentDTO;
import com.boot.hotel.oauth2.dto.SessionUser;
import com.boot.hotel.service.HotelDetailService;
import com.boot.hotel.service.PaymentService;

@RestController
public class PaymentController {
	
	@Resource
	private PaymentService paymentService;
	
	@Resource
	private HotelDetailService hotelDetailService;
	
    @Autowired
    private HttpServletRequest request;
	
    @Autowired
	private HttpSession httpSession;
	
	
	@GetMapping("/dummyTest")
	public ModelAndView aboutUs() throws Exception{
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> paramMap = new HashMap<>();
		
		int hotelId = 35/*hotel_id*/;
		String type = "title";
		paramMap.put("hotel_id", hotelId);
		paramMap.put("type", type);
		
		List<String> hotelTitle = paymentService.searchHotelTitle(paramMap);
		mav.addObject("hotelTitle",hotelTitle);
		

		String user_name = "배수지";
		String userid = "suzi";
		String hotel_name = "aaa";
		String room ="스탠다드";
		int people = 2;
		int date_num = 2;
		String category = "호텔";
		String status = "결제완료";
		int pay_num = 1;
		int res_num = 1;
		String request1 = null;
		int price = 100;
		int inq_date = 20000101;
		
		mav.addObject("user_name",user_name);
		mav.addObject("userid",userid);
		mav.addObject("hotel_name",hotel_name);
		mav.addObject("room",room);
		mav.addObject("people",people);
		mav.addObject("date_num",date_num);
		mav.addObject("category",category);
		mav.addObject("status",status);
		mav.addObject("pay_num",pay_num);
		mav.addObject("res_num",res_num);
		mav.addObject("request1",request1);
		mav.addObject("price",price);
		mav.addObject("inq_date",inq_date);
		
		mav.setViewName("payment/dummy");
		return mav;
	}
	
	@PostMapping("payment/dummy")
	public ModelAndView myAccommodations(PaymentDTO payDto, HotelReservationDTO resDto) throws Exception{
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("payment/dummy");
		return mav;
	}
	
	@GetMapping("/payChek")
	public ModelAndView payChek() throws Exception{
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("payment/payChek");
		return mav;
	}
	
//	@GetMapping("/paymentPage")
	@RequestMapping(value = "/paymentPage", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView paymentPage(
			@RequestParam("room_type") String room
			,@RequestParam("hotel_id") int hotel_id
			,@RequestParam("check_in") String check_in
			,@RequestParam("check_out") String check_out
			) throws Exception{
		// 받아와야 하는 거 - userid, hotel_id, room, inq_date, date_num
		// 체크인 체크아웃 날짜 받아서 date_num 받기
		
		
		System.out.println("결제하기 페이지 이동");
		
		ModelAndView mav = new ModelAndView(); 
		
		List<HotelInfoDTO> hotInfoDto = hotelDetailService.getHotelInfoById(hotel_id);
		
		mav.addObject("hotInfoDto", hotInfoDto);
		
		Map<String, Object> paramMap = new HashMap<>();
		
		// 방종류 - 넘겨 받기
		String rmType = room/*방 종류*/;
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
    	String userid = sessionUser.getId();
		
		String type = "title";
		paramMap.put("hotel_id", hotel_id);
		paramMap.put("type", type);
		
		List<String> hotelTitle = paymentService.searchHotelTitle(paramMap);
		
		List<Map<String,Object>> hotelInfo = paymentService.getHotelInfo(hotel_id);
		
		Object category = hotelInfo.get(0).get("CATEGORY");
		Object hotel_name = hotelInfo.get(0).get("HOTEL_NAME");
		Object check_inTime = hotelInfo.get(0).get("CHECK_IN");
		Object check_outTime = hotelInfo.get(0).get("CHECK_OUT");
		
		int price = 0;
		String roomType = rmType;
		if (roomType.equals("standard")) {
		    price = ((BigDecimal) hotelInfo.get(0).get("STANDARD")).intValue();
		} else if (roomType.equals("sweet")) {
		    price = ((BigDecimal) hotelInfo.get(0).get("SWEET")).intValue();
		} else if (roomType.equals("deluxe")) {
		    price = ((BigDecimal) hotelInfo.get(0).get("DELUXE")).intValue();
		}
		
		List<Map<String, Object>> userInfo = paymentService.getUserInfo(userid);
		Object user_id = userInfo.get(0).get("USERID");
		Object username = userInfo.get(0).get("USERNAME");
		Object tel = userInfo.get(0).get("TEL");
		Object email = userInfo.get(0).get("EMAIL");
		
		// 숙박날짜 - 넘겨 받기 
		// 체크인 체크아웃 날짜 받기
		
		
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		Date checkInDate = dateFormat1.parse(check_in);
		Date checkOutDate = dateFormat1.parse(check_out);
		// TODO - 날짜 받기
//		Date checkInDate = checkIn;
//		Date checkOutDate = checkOut;
		

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(checkInDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(checkOutDate);

		long diffMillis = endCal.getTimeInMillis() - startCal.getTimeInMillis();
		long date_num = diffMillis / (24 * 60 * 60 * 1000);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(checkInDate); // Calendar 객체에 변환한 날짜를 적용

		calendar.add(Calendar.DATE, (int) date_num); // date_num일을 더함

		// 숙박인원
		int people = 0;
		/*스탠다드 스위트면 2명 디럭스는 4명나오게 */
		if (rmType.equals("standard") || rmType.equals("sweet")) {
		    people = 2;
		} else if (rmType.equals("deluxe")) {
		    people = 4;
		}
		// 결제번호 / 예약번호
		int maxResNum = paymentService.maxResNum();
		int maxPayNum = paymentService.maxPayNum();
		
		mav.addObject("maxResNum",maxResNum+1);
		mav.addObject("maxPayNum",maxPayNum+1);
		
		mav.addObject("hotel_name",hotel_name);
		mav.addObject("hotel_id",hotel_id);
		mav.addObject("room",rmType);
		mav.addObject("category",category);
		mav.addObject("price",price);
		mav.addObject("hotelInfo",hotelInfo);
		mav.addObject("people",people);
		mav.addObject("hotelTitle",hotelTitle);
		
		mav.addObject("check_inTime",check_inTime);
		mav.addObject("check_outTime",check_outTime);
		
		mav.addObject("check_in",checkInDate);
		mav.addObject("check_out",checkOutDate);
		mav.addObject("date_num",date_num);
		
		mav.addObject("userInfo",userInfo);
		mav.addObject("tel",tel);
		mav.addObject("email",email);	
		
		mav.addObject("userid",user_id);
		mav.addObject("username",username);
		
		mav.setViewName("payment/paymentPage");
		return mav;
	}
	
	
	@RequestMapping(value = "/payment/payment_ok", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView booking_ok(PaymentDTO payDto, HotelReservationDTO resDto, HttpServletRequest request
			) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> paramMap = new HashMap<>();
		
		
		List<Map<String,Object>> hotelInfo = paymentService.getHotelInfo(resDto.getHotel_id());
		
		Object check_inTime = hotelInfo.get(0).get("CHECK_IN");
		Object check_outTime = hotelInfo.get(0).get("CHECK_OUT");
		
		mav.addObject("check_inTime", check_inTime);
		mav.addObject("check_outTime", check_outTime);
		
		
		int hotelId = resDto.getHotel_id();
		String type = "title";
		paramMap.put("hotel_id", hotelId);
		paramMap.put("type", type);
	    
		List<String> hotelTitle = paymentService.searchHotelTitle(paramMap);
		mav.addObject("hotelTitle",hotelTitle);
		
		List<Map<String, Object>> userInfo = paymentService.getUserInfo(resDto.getUserid());
		
		Object username = userInfo.get(0).get("USERNAME");
		Object userId = userInfo.get(0).get("USERID");
		
		mav.addObject("username",username);
		mav.addObject("userId",userId);
		
		
		String maxResNumStr = request.getParameter("maxResNum");
		int maxResNum = Integer.parseInt(maxResNumStr);
		
		String maxPayNumStr = request.getParameter("maxPayNum");
		int maxPayNum = Integer.parseInt(maxPayNumStr);
		
		resDto.setRes_num(maxResNum);
		payDto.setPay_num(maxPayNum);
		
		resDto.getRes_num();
		
		resDto.setStatus("결제완료");
		
		payDto.setRes_num(resDto.getRes_num());
		payDto.setUserid1(resDto.getUserid());
		payDto.setUsername1(resDto.getUsername());
		payDto.setHotel_id1(resDto.getHotel_id());
		payDto.setPeople1(resDto.getPeople());
		
		paymentService.insertReservation(resDto);
		paymentService.insertPayment(payDto);
		
		mav.addObject("payDto",payDto);
		mav.addObject("resDto",resDto);
		
		
		mav.setViewName("payment/payChek");
		
		return mav;
	}
	
}

