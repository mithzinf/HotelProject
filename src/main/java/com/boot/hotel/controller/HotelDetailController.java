package com.boot.hotel.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelFacilityDTO;
import com.boot.hotel.dto.HotelFacilityInDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.HotelReservationDTO;
import com.boot.hotel.oauth2.dto.SessionUser;
import com.boot.hotel.service.HotelDetailService;
@ResponseBody
@RestController
public class HotelDetailController {
   
   @Resource
   private HotelDetailService hotelDetailService;
   
   @Autowired
   private HttpSession httpSession;
/*
   @RequestMapping(value = "/detail" , method= {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView detail(@RequestParam(value = "hotel_id", required = true) int hotel_id, 
				@RequestParam(value = "type", required = true) String type) throws Exception {
     
     
      
      Map<String, Object> paramMap = new HashMap<>();
	   
 	 
	   //hotel_id와 type을 paramMap에 넣어요....매개변수를 넣는다......
	   paramMap.put("hotel_id", hotel_id);
	   paramMap.put("type", type);
	   
	   List<String> searchHotelDetail = hotelDetailService.searchHotelDetail(paramMap); 
     
      
      List<HotelDTO> dto1 = hotelDetailService.getHotelById(hotel_id);
      List<HotelInfoDTO> dto2 = hotelDetailService.getHotelInfoById(hotel_id);
      List<HotelPictureDTO> dto3 = hotelDetailService.getHotelPicById(hotel_id);
      List<HotelFacilityInDTO> dto4 = hotelDetailService.getHotelFacilityInById(hotel_id);
      
      
      ModelAndView mav = new ModelAndView();
      // Model에 데이터 추가, 좌항 : view에서 부를 별칭, 우항 : 진짜 담아진 객체의 이름
      mav.addObject("dto1", dto1);
      mav.addObject("dto2", dto2);
      mav.addObject("dto3", dto3);
      mav.addObject("dto4", dto4);
      mav.addObject("searchHotelDetail",searchHotelDetail);
      //mav.addObject("hotel_id",hotel_id);
      mav.setViewName("hotel/detail"); //templates.login의 detail.html로 가게 하려고...
      
      return mav;
      
   }
   
  */ 
   
   
    @RequestMapping(value = "/detail", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView detail(@RequestParam("hotel_id") int hotel_id, HttpServletRequest request) throws Exception {
      
		String check_in_list = request.getParameter("check_in");
		String check_out_list = request.getParameter("check_out");
		 
		//체크인, 체크아웃 시간을 받아 알맞은 형태로 바꾸고 변수에 저장
		LocalDate check_in_day = LocalDate.parse(check_in_list, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate check_out_day = LocalDate.parse(check_out_list, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 
		//숙박일수 계산
		int days = (int) ChronoUnit.DAYS.between(check_in_day, check_out_day);
		
		//아래 날짜에 따른 만실 체크를 위해 미리 준비
		LocalDate check_in_temp = check_in_day;
		LocalDate check_out_temp = check_in_day.plusDays(1);
		int standard = 0;
		int sweet = 0;
		int deluxe = 0;
		String standardJudge = "";
		String sweetJudge = "";
		String deluxeJudge = "";
		
		Map<String, Object> resultMap1 = new HashMap<String, Object>();
		
		//스탠다드룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			standard = hotelDetailService.searchDayStandard(paramMap2);
			
			
			if(standard>4) {
				standardJudge = "만실";
				break;
			}else if(standard == 4) {
				standardJudge = "만실 임박";
			}else {
				if(standardJudge!="만실 임박") {
					standardJudge = "여유";
				}
			}
		}
		
		//스위트룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			sweet = hotelDetailService.searchDaySweet(paramMap2);
			
			if(sweet>4) {
				sweetJudge = "만실";
				break;
			}else if(sweet == 4) {
				sweetJudge = "만실 임박";
			}else {
				if(!sweetJudge.equals("만실 임박")) {
					sweetJudge = "여유";
				}
			}
		}
		
		//디럭스룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			deluxe = hotelDetailService.searchDayDeluxe(paramMap2);
			
			if(deluxe>4) {
				deluxeJudge = "만실";
				break;
			}else if(deluxe == 4) {
				deluxeJudge = "만실 임박";
			}else {
				if(deluxeJudge!="만실 임박") {
					deluxeJudge = "여유";
				}
			}
		}
			
			
		
		//잔여객실 체크후 각방의 상태를 json형태로 담아서 보낼 준비
		if(standardJudge.equals("만실")) {
			resultMap1.put("standard", "만실");
		}
		else if(standardJudge.equals("만실 임박")){
			resultMap1.put("standard", "만실 임박");
		}else {
			resultMap1.put("standard", "여유");
		}
		
		if(sweetJudge.equals("만실")) {
			resultMap1.put("sweet", "만실");
		}
		else if(sweetJudge.equals("만실 임박")){
			resultMap1.put("sweet", "만실 임박");
		}else {
			resultMap1.put("sweet", "여유");
		}
		
		if(deluxeJudge.equals("만실")) {
			resultMap1.put("deluxe", "만실");
		}
		else if(deluxeJudge.equals("만실 임박")){
			resultMap1.put("deluxe", "만실 임박");
		}else {
			resultMap1.put("deluxe", "여유");
		}
		
		 
		List<HotelDTO> dto1 = hotelDetailService.getHotelById(hotel_id);
		List<HotelInfoDTO> dto2 = hotelDetailService.getHotelInfoById(hotel_id);
		List<HotelFacilityDTO> dto3 = hotelDetailService.getHotelFacilityById(hotel_id);
		List<HotelFacilityInDTO> dto4 = hotelDetailService.getHotelFacilityInById(hotel_id);
		//List<HotelPictureDTO> searchHotelDetail = hotelDetailService.searchHotelDetail(paramMap);
		List<HotelPictureDTO> getTitle = hotelDetailService.getTitlePicture(hotel_id); //타이틀 사진 갖구와...
		List<HotelPictureDTO> getStandard = hotelDetailService.getStandardPicture(hotel_id); //스탠다드 사진 가지구 와..
		List<HotelPictureDTO> getDeluxe = hotelDetailService.getDeluxePicture(hotel_id); //디럭스 사진 갖고오렴..
		List<HotelPictureDTO> getSweet = hotelDetailService.getSweetPicture(hotel_id); //스위트룸 사진 가지구 와..
		  
		
		

		
    	
		ModelAndView mav = new ModelAndView();
		if(httpSession.getAttribute("sessionUser")!=null) {
        	SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        	mav.addObject("user_id",sessionUser.getId());
        }
		mav.addObject("resultMap1",resultMap1);
		mav.addObject("check_in_day",check_in_day);
		mav.addObject("check_out_day",check_out_day);
		mav.addObject("hotel_id", hotel_id);
		mav.addObject("dto1", dto1);
		mav.addObject("dto2", dto2);
		mav.addObject("dto3", dto3);
		mav.addObject("dto4", dto4);
		mav.addObject("getTitle", getTitle);
		mav.addObject("getStandard", getStandard);
		mav.addObject("getDeluxe", getDeluxe);
		mav.addObject("getSweet", getSweet);
		System.out.println(resultMap1);
		mav.setViewName("hotel/detail");
			
		return mav;
			 
	}

   //상세페이지에서 예약하기를 눌렀을 때의 메소드!...hotel_id, 체크인날짜, 체크아웃날짜, 예약한 객실(standard,suite,deluxe)의 타입 데이터 넘길 메소드
   //딱히 매핑주소가 필요하진 않아보임?
 //hotel_id는 hotelDTO에 있고...
   //check_in 날짜와 check_out 날짜는 장바구니 테이블 basketdto의 req_date, reservation 테이블의 inq_date 컬럼 갖다 써야하는지?
   //내가 예약한 객실..room 컬럼 room == standard 뭐 이렇게 넘겨야 하는것임? 
   //테이블 안써도 되고..detail.html부분에서 체크인, 체크아웃 날짜 선택하는 부분에서 그 날짜를 value name으로 받아서 넘기면 될듯?
   //결제페이지에서는 서치밸류 넘기지 말자고 하숏다, roomtype 일일이 치면 된다... 리퀘스트파람을 room1 room2 name 은 type(예약어여서 type1)으로 / value = standard 
   @RequestMapping(value = "/bookRoom", method = RequestMethod.POST)
   public ModelAndView bookRoom(@RequestParam("hotel_id") int hotel_id,@RequestParam("room_type") String room_type,HttpServletRequest request) throws Exception {
	   		
	   
	   		ModelAndView mav = new ModelAndView();
	   		
	 
	   		//일단 체크인,체크아웃 날짜는 내가 정해놓은 것임
	   		/*
	   		Calendar startCal = Calendar.getInstance();
			startCal.setTime(checkInDate);

			Calendar endCal = Calendar.getInstance();
			endCal.setTime(checkOutDate);
	   		 */
	   		 
	   	
	     
	     //날짜 계산
	     SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
	     Date checkInDate = dateformat.parse("2023/04/26");
	     Date checkOutDate =dateformat.parse("2023/04/28");
	     
	     	Calendar startCal = Calendar.getInstance();
			startCal.setTime(checkInDate);

			Calendar endCal = Calendar.getInstance();
			endCal.setTime(checkOutDate);
	     
			
			
			long diffMillis = endCal.getTimeInMillis() - startCal.getTimeInMillis();
			long date_num = diffMillis / (24 * 60 * 60 * 1000);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(checkInDate); // Calendar 객체에 변환한 날짜를 적용

			calendar.add(Calendar.DATE, (int) date_num); // date_num일을 더함
	     
	     
		    
	     List<HotelInfoDTO> dto2 = hotelDetailService.getHotelInfoById(hotel_id); //hotel_id 델꼬 올라고 일단 이거 가지고 와밧슴 리스트로..
	     
	     	mav.addObject("dto2", dto2);
		    mav.addObject("checkInDate", checkInDate);
		    mav.addObject("checkOutDate", checkOutDate);
		    mav.addObject("date_num", date_num);
		    mav.addObject("room_type", room_type);
			System.out.println("객실 예약 버튼 클릭");

			System.out.println("호텔아이디 : "+ hotel_id);
			mav.setViewName("payment/paymentPage");   

	   return mav;
   }
 
		   
	@ResponseBody
	@RequestMapping(value = "/detailDay", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> detailDay(@RequestParam String check_in, @RequestParam String check_out,
			@RequestParam int hotel_id) throws Exception {
   
	    Map<String, Object> resultMap = new HashMap<String, Object>();
	   
		//체크인, 체크아웃 시간을 받아 알맞은 형태로 바꾸고 변수에 저장
		LocalDate check_in_day = LocalDate.parse(check_in, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate check_out_day = LocalDate.parse(check_out, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		//숙박일수 계산
		int days = (int) ChronoUnit.DAYS.between(check_in_day, check_out_day);
		
		//아래 날짜에 따른 만실 체크를 위해 미리 준비
		LocalDate check_in_temp = check_in_day;
		LocalDate check_out_temp = check_in_day.plusDays(1);
		int standard = 0;
		int sweet = 0;
		int deluxe = 0;
		String standardJudge = "";
		String sweetJudge = "";
		String deluxeJudge = "";
		
		
		//스탠다드룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			standard = hotelDetailService.searchDayStandard(paramMap2);
			
			
			if(standard>4) {
				standardJudge = "만실";
				break;
			}else if(standard == 4) {
				standardJudge = "만실 임박";
			}else {
				if(standardJudge!="만실 임박") {
					standardJudge = "여유";
				}
			}
		}
		
		//스위트룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			sweet = hotelDetailService.searchDaySweet(paramMap2);
			
			if(sweet>4) {
				sweetJudge = "만실";
				break;
			}else if(sweet == 4) {
				sweetJudge = "만실 임박";
			}else {
				if(sweetJudge!="만실 임박") {
					sweetJudge = "여유";
				}
			}
		}
		
		//디럭스룸 잔여객실 체크
		for(int i=0;i<days;i++) {
			
			Map<String, Object> paramMap2 = new HashMap<>();
			paramMap2.put("check_in", check_in_temp.plusDays(i));
			paramMap2.put("check_out", check_out_temp.plusDays(i));
			paramMap2.put("hotel_id", hotel_id);
			deluxe = hotelDetailService.searchDayDeluxe(paramMap2);
			
			if(deluxe>4) {
				deluxeJudge = "만실";
				break;
			}else if(deluxe == 4) {
				deluxeJudge = "만실 임박";
			}else {
				if(deluxeJudge!="만실 임박") {
					deluxeJudge = "여유";
				}
			}
		}
			
			
		
		//잔여객실 체크후 각방의 상태를 json형태로 담아서 보낼 준비
		if(standardJudge.equals("만실")) {
			resultMap.put("standard", "만실");
		}
		else if(standardJudge.equals("만실 임박")){
			resultMap.put("standard", "만실 임박");
		}else {
			resultMap.put("standard", "여유");
		}
		
		if(sweetJudge.equals("만실")) {
			resultMap.put("sweet", "만실");
		}
		else if(sweetJudge.equals("만실 임박")){
			resultMap.put("sweet", "만실 임박");
		}else {
			resultMap.put("sweet", "여유");
		}
		
		if(deluxeJudge.equals("만실")) {
			resultMap.put("deluxe", "만실");
		}
		else if(deluxeJudge.equals("만실 임박")){
			resultMap.put("deluxe", "만실 임박");
		}else {
			resultMap.put("deluxe", "여유");
		}

		   
		return resultMap;
	   
   }
   
 
   
   
}




