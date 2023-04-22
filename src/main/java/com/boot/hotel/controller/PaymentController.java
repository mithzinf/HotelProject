package com.boot.hotel.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.text.DateFormat;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelReservationDTO;
import com.boot.hotel.dto.PaymentDTO;
import com.boot.hotel.service.PaymentService;

@RestController
public class PaymentController {
	
	@Resource
	private PaymentService paymentService;
	
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
	
	@GetMapping("/paymentPage")
	public ModelAndView paymentPage() throws Exception{
		// 받아와야 하는 거 - userid, hotel_id, inq_date, people, room, date_num
		
		ModelAndView mav = new ModelAndView(); 
		
		Map<String, Object> paramMap = new HashMap<>();
		
		
		int hotelId = 35/*hotel_id*/;
		String type = "title";
		paramMap.put("hotel_id", hotelId);
		paramMap.put("type", type);
		
		List<String> hotelTitle = paymentService.searchHotelTitle(paramMap);
		mav.addObject("hotelTitle",hotelTitle);
		
		List<Map<String,Object>> hotelInfo = paymentService.getHotelInfo(35);/*hotel_id*/
		
		Object category = hotelInfo.get(0).get("CATEGORY");
		Object hotel_name = hotelInfo.get(0).get("HOTEL_NAME");
		Object hotel_id = hotelInfo.get(0).get("HOTEL_ID"); // hotel_id 받아오면 필요 없다
		Object check_in = hotelInfo.get(0).get("CHECK_IN");
		Object check_out = hotelInfo.get(0).get("CHECK_OUT");
		
		
		mav.addObject("check_in",check_in);
		mav.addObject("check_out",check_out);
		
		
		int price = 0;
		String roomType = "디럭스"/*방종류받기*/;
		if (roomType.equals("스탠다드")) {
		    price = ((BigDecimal) hotelInfo.get(0).get("STANDARD")).intValue();
		} else if (roomType.equals("스위트")) {
		    price = ((BigDecimal) hotelInfo.get(0).get("SWEET")).intValue();
		} else if (roomType.equals("디럭스")) {
		    price = ((BigDecimal) hotelInfo.get(0).get("DELUXE")).intValue();
		}
		
		
		List<Map<String, Object>> userInfo = paymentService.getUserInfo("suzi"/*userd*/);
		Object username = userInfo.get(0).get("USERNAME");
		Object userId = userInfo.get(0).get("USERID");
		Object tel = userInfo.get(0).get("TEL");
		Object email = userInfo.get(0).get("EMAIL");
		
		mav.addObject("username",username);
		mav.addObject("userId",userId);
		mav.addObject("tel",tel);
		mav.addObject("email",email);
		
		mav.addObject("hotelInfo",hotelInfo);
		mav.addObject("userInfo",userInfo);
		
		// 숙박일수 - 넘겨 받기
		int date_num = 1/*date_num*/;
		
		// 숙박날짜 - 넘겨 받기 
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
		Date inqDate1 = dateFormat1.parse("2024/02/15"/*inq_date*/);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inqDate1); // Calendar 객체에 변환한 날짜를 적용

		calendar.add(Calendar.DATE, date_num); // 5일을 더함

		Date resultDate = calendar.getTime(); // 더한 날짜를 Date 형식으로 가져옴

		String checkOutDate = dateFormat1.format(resultDate); // Date 형식을 다시 문자열로 변환
		
		// 방종류 - 넘겨 받기
		String room = "디럭스"/*방 종류*/;
		mav.addObject("room",room);
		
		mav.addObject("price",price);
		
		// 숙박인원
		int people = 2;
		
		int maxResNum = paymentService.maxResNum();
		int maxPayNum = paymentService.maxPayNum();
		
		mav.addObject("maxResNum",maxResNum+1);
		mav.addObject("maxPayNum",maxPayNum+1);
		
		mav.addObject("hotel_id",hotel_id);
		mav.addObject("people",people);
		mav.addObject("inqDate1",inqDate1);
		mav.addObject("category",category);
		mav.addObject("hotel_name",hotel_name);
		mav.addObject("inqDate1",inqDate1);
		mav.addObject("checkOutDate",checkOutDate);
		mav.addObject("date_num",date_num);
		mav.setViewName("payment/payment");
		
		mav.setViewName("payment/paymentPage");
		return mav;
	}
	
	
	@RequestMapping(value = "/payment/payment_ok", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView booking_ok(PaymentDTO payDto, HotelReservationDTO resDto, HttpServletRequest request
			) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		
		Map<String, Object> paramMap = new HashMap<>();
		
		int hotelId = resDto.getHotel_id() /*35 hotel_id*/;
		String type = "title";
		paramMap.put("hotel_id", hotelId);
		paramMap.put("type", type);
	    
		List<String> hotelTitle = paymentService.searchHotelTitle(paramMap);
		mav.addObject("hotelTitle",hotelTitle);
		
		
		List<Map<String, Object>> userInfo = paymentService.getUserInfo(resDto.getUserid()/*"suzi" userid*/);
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
		
		paymentService.createReservation(resDto);
		paymentService.insertPayment(payDto);
		
		mav.addObject("payDto",payDto);
		mav.addObject("resDto",resDto);
		
		
		mav.setViewName("payment/payChek");
		
		return mav;
	}
	
}

