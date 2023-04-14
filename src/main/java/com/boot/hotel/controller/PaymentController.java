package com.boot.hotel.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.HotelReservationDTO;
import com.boot.hotel.dto.MemberDTO;
import com.boot.hotel.dto.PaymentDTO;
import com.boot.hotel.service.PaymentService;
import com.nimbusds.jose.shaded.json.JSONObject;

/*
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
*/
@RestController
public class PaymentController {
	
	@Resource
	private PaymentService paymentService;
	
	@GetMapping("/aboutUs")
	public ModelAndView aboutUs() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/payment/about-us");
		return mav;
	}
	
	@GetMapping("/detailing")
	public ModelAndView detail() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/payment/detail");
		return mav;
	}
	
	@GetMapping("/blog")
	public ModelAndView blog() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/payment/blog");
		return mav;
	}
	
	@GetMapping("/reviews")
	public ModelAndView reviews() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/payment/reviews");
		return mav;
	}
	
	@GetMapping("/reservation")
	public ModelAndView reservation() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/payment/reservations");
		return mav;
	}
	
	@GetMapping("/listing-matrix")
	public ModelAndView listingMatrix() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/payment/listing-matrix");
		return mav;
	}
	
	@GetMapping("/accommodations")
	public ModelAndView myAccommodations() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/payment/my-accommodations");
		return mav;
	}
	
	
	@GetMapping("/payment")
	public ModelAndView booking(PaymentDTO payDto, HotelReservationDTO resDto,
			HotelPictureDTO picDto, Model model) throws Exception{
		System.out.println("결제 페이지로 이동");
		ModelAndView mav = new ModelAndView();
		
	    // model.addAttribute("hotel_name", "aaa");
		
		// 이 부분은 상세페이지에서 받아오면 필요없다
		// inq_date 부분 해결 해야 한다
		
		int res_num = 2;
		int hotel_id = 2;
		int price = 100;
		int date_num = 2;
		int people = 2;
		int inq_date = 20230101;
		String category = "호텔";
		String room = "스탠다드";
		
		
		String[] testArray={"res_num", "hotel_id", "price","date_num","people","inq_date","category","room"};
		
		model.addAttribute(testArray);
		
	    model.addAttribute("pay_num", 1);
	    model.addAttribute("res_num", 1);//
	    model.addAttribute("hotel_id", 1);//
	    model.addAttribute("userid", "a");
	    model.addAttribute("username", "배수지");
	    model.addAttribute("method", "card");
	    model.addAttribute("price", 100);//
	    model.addAttribute("date_num", 1);//
	    model.addAttribute("people", 2);//
	    model.addAttribute("inq_date", 20230101); //
	    model.addAttribute("category", "호텔");//
	    model.addAttribute("hotel_name", "호텔이름");
	    model.addAttribute("room", "스탠다드");//
	    model.addAttribute("request", "테스트값입니다");
	    
	    model.addAttribute("tel","010-1234-1234");
	    model.addAttribute("email", "wojoo729@naver.com");
	    
	    
	    // 결제 상세 정보에 보일 변수
	    // 예약인원 숙박일수 가격 방종류 예약자이름 호텔이름
	    // 호텔이름/방종류는 2개 이상일 수 있으니 배열로 받는다
	    // 예약인원, 숙박일수, 가격은 최종 값을 변수에 담는다
	    // 예매자 이름은 한명으로 통일한다
	    
	    
	    
	    
	    mav.setViewName("/payment/payment");
	    
		return mav;
		
	}
	
	@RequestMapping(value = "/payment/payment_ok", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView booking_ok(PaymentDTO payDto, HotelReservationDTO resDto, HttpServletRequest request) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
//		int maxResNum = paymentService.maxResNum();
//		int maxPayNum = paymentService.maxPayNum();
		
//		resDto.setRes_num(maxResNum+1);
//		payDto.setPay_num(maxPayNum+1);
		
//		결제 상태는 임의로 완료로 표시하고 나중에 조건문 추가한다
		
		resDto.setStatus("결제완료");
		
		payDto.setUserid1(resDto.getUserid());
		payDto.setUsername1(resDto.getUsername());
		payDto.setHotel_id1(resDto.getHotel_id());
		payDto.setPeople1(resDto.getPeople());
		
		/*
	    String inq_date = request.getParameter("inq_date");
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	    Date date = format.parse(inq_date);
		resDto.setInq_date(date);
		*/
		
		paymentService.createReservation(resDto);
		paymentService.insertPayment(payDto);
		
		mav.addObject("payDto",payDto);
		mav.addObject("resDto",resDto);
		
		mav.setViewName("payment/payChek");
		
		return mav;
	}
	
	/*
	// 결제 API
	// 콜백 수신처리
	@RequestMapping("/payment/callback_recieve")
	public ModelAndView callback_recieve(@RequestBody Map<String,Object> model, 
			PaymentDTO payDto, HotelReservationDTO resDto, HttpServletRequest request){
//	public ModelAndView callback_recieve(@RequestBody Map<String,Object> model, 
//			PaymentDTO payDto, HotelReservationDTO resDto, HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		
		String process_result = "결제성공";
		// 응답 header 생성
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		JSONObject responseObj = new JSONObject();
		
		try {
			String imp_uid = (String)model.get("imp_uid");
			String merchant_uid = (String)model.get("merchant_uid");
			boolean success = (Boolean)model.get("success");
			String error_msg = (String)model.get("error_msg");
			String status = (String)model.get("status");
			String hotel_name = (String)model.get("name");
			String username = (String)model.get("buyer_name");
			
			resDto.setUsername(username);
			resDto.setHotel_name(hotel_name);
			
			System.out.println("------callback receive------");
			System.out.println("----------------------------");
			System.out.println("imp_uid : " + imp_uid);
			System.out.println("merchant_uid : " + merchant_uid);
			System.out.println("success : " + success);
			System.out.println("status2 : " + status);
			System.out.println("name : " + hotel_name);
			
			if(success == true) {
				
				// db select (select amount from oder_table where merchant_uid = ?)
				
				String api_key = "5806138262814682";
				String api_secret = "oT8TWZV7vG4cV42hQkAlT0eDMraud0YCm9h0bEQVtRc1D5Un4xJji7KtN7yGSdVdu9qMuUEXsfl9xU6Y";
				
				IamportClient ic = new IamportClient(api_key,api_secret);
				IamportResponse<Payment> response = ic.paymentByImpUid(imp_uid);
				
				BigDecimal iamport_amount = response.getResponse().getAmount();
				// compare db_amount and api_ampount
				// if(db_amount==api_amount)
				
				responseObj.put("process_result", "결제성공");
				// db save (update oder_table set pay_result = 'success', imp_uid? where merchant_uid = ?)
				// responseObj.put("process_result","결제위변조");
				// else{ result = "fail"; cancel API}
				
				// 상세 페이지랑 연결해서 데이터를 받아올때 삭제한다
				// 임의로 넣는 데이터
				payDto.setDate_num(1);
				payDto.setPay_num(1);
				payDto.setRes_num(1);
				resDto.setHotel_id(1);
				resDto.setUserid("a");
				payDto.setMethod("card");
				resDto.setPeople(2);
			    resDto.setCategory("호텔");
				resDto.setRoom("스탠다드");
				resDto.setRequest("호텔예약입니다");
				payDto.setPrice(100);
				
//				resDto.setUsername("배수지");
				
//				결제 상태는 임의로 완료로 표시하고 나중에 조건문 추가한다
				resDto.setStatus("결제완료");
				
				payDto.setUserid1(resDto.getUserid());
				payDto.setUsername1(resDto.getUsername());
				payDto.setHotel_id1(resDto.getHotel_id());
				payDto.setPeople1(resDto.getPeople());
				
				paymentService.createReservation(resDto);
				paymentService.insertPayment(payDto);
				
				mav.addObject("payDto",payDto);
				mav.addObject("resDto",resDto);
				
				mav.setViewName("redirect:/payment/payChek");
				
				System.out.println(mav.getViewName());
				
				return mav;
				
			}else {
				System.out.println("error_msg : " + error_msg);
				responseObj.put("process_result", "결제실패 : " + error_msg);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			responseObj.put("process_result", "결제실패 : 관리자에게 문의하기");
			
		}
//		return new ResponseEntity<String>(responseObj.toString(), responseHeaders, HttpStatus.OK);
		return mav;
		
	}
	//return new ResponseEntity<String>(responseObj.toString(), responseHeaders, HttpStatus.OK);
	*/
	
	@RequestMapping(value = "/payment/payChek",
			method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView payChek() throws Exception{
	    System.out.println("/*/*/*/*/*/*/*/*=");
	    ModelAndView mav = new ModelAndView();
	    // 태이블에서 받아오기
	    
	    mav.setViewName("payment/payChek");
	    
	    return mav;
	}
	
	
	

	@GetMapping("/apiTest")
	public ModelAndView apiTest(Model model) throws Exception{
		System.out.println("결제 페이지로 이동");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("payment/apiTest");
		
		return mav;
		
	}
	
	@GetMapping("/paymentTest")
	public ModelAndView paymentTest(Model model) throws Exception{
		System.out.println("결제 페이지로 이동");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("payment/paymentTest");
		
		return mav;
		
	}
	
	
	@GetMapping("/payTest")
	public ModelAndView payTest(Model model) throws Exception{
		System.out.println("결제 페이지로 이동");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("payment/payTest");
		
		return mav;
		
	}
	
	

	/*
	// 웹훅 수신 처리
	@RequestMapping("/payment/webhook_receive")
	public ResponseEntity<?> webhook_receive(@RequestBody Map<String, Object> model){
		
		// 응답 header 생성
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		
		try {
			String imp_uid = (String)model.get("imp_uid");
			String merchant_uid = (String)model.get("merchant_uid");
			boolean success = (Boolean)model.get("success");
			String error_msg = (String)model.get("error_msg");
			String status = (String)model.get("status");
			
			
			System.out.println("------callback receive------");
			System.out.println("----------------------------");
			System.out.println("imp_uid : " + imp_uid);
			System.out.println("merchant_uid : " + merchant_uid);
			System.out.println("success : " + success);
			System.out.println("status1 : " + status);
			
			
			
			// db select (select amount from oder_table where merchant_uid = ?)
			
			String api_key = "5806138262814682";
			String api_secret = "oT8TWZV7vG4cV42hQkAlT0eDMraud0YCm9h0bEQVtRc1D5Un4xJji7KtN7yGSdVdu9qMuUEXsfl9xU6Y";
			
			IamportClient ic = new IamportClient(api_key,api_secret);
			IamportResponse<Payment> response = ic.paymentByImpUid(imp_uid);
			// compare db_amount and api_ampount
			// if(db_amount==api_amount)
			
			// db save (update oder_table set pay_result = 'success', 'imp_uid=? where merchant_uid = ?)
			// else {result = "fail";
			// alert to manager!!!!
			// return new ResponseEnity (new CustomErrorType("금액위변조"), HttpStatus.INTERNAL_SERVER_ERROR);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			// CustomErrorType 이걸 못가져온다. 왜인지는 일단 보류
			// return new ResponseEntity(new CustomErrorType("서버처리 오류"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>("결과반영 성공", responseHeaders,HttpStatus.OK);
		
		
		
	}
	*/
	
	
	
	
}














