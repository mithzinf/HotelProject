package com.boot.hotel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.boot.hotel.dto.HotelFacilityInDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.HotelReservationDTO;
import com.boot.hotel.service.HotelDetailService;
@ResponseBody
@RestController
public class HotelDetailController {
   
   @Resource
   private HotelDetailService hotelDetailService;
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
   public ModelAndView detail(@RequestParam("hotel_id") int hotel_id) throws Exception {
      
	  
      Map<String, Object> paramMap = new HashMap<>();
      paramMap.put("hotel_id", hotel_id);
      
      List<HotelDTO> dto1 = hotelDetailService.getHotelById(hotel_id);
      List<HotelInfoDTO> dto2 = hotelDetailService.getHotelInfoById(hotel_id);
      List<HotelFacilityInDTO> dto4 = hotelDetailService.getHotelFacilityInById(hotel_id);
      List<HotelPictureDTO> searchHotelDetail = hotelDetailService.searchHotelDetail(paramMap);
      
      String[] urlname = new String[searchHotelDetail.size()];
      int i = 0;
      
      
      
      for (HotelPictureDTO url: searchHotelDetail) {
          urlname[i] = String.valueOf(url); 
          System.out.println("urlname[i] = " +urlname[i]); 
          i++;
      } 
      
		System.out.println(searchHotelDetail);
      
      ModelAndView mav = new ModelAndView();
      mav.addObject("hotel_id", hotel_id);
      mav.addObject("dto1", dto1);
      mav.addObject("dto2", dto2);
      mav.addObject("dto4", dto4);
      mav.addObject("searchHotelDetail", searchHotelDetail);
      mav.addObject("urlname", urlname);
      mav.setViewName("hotel/detail");
      
      return mav;
   }

   //상세페이지에서 예약하기를 눌렀을 때의 메소드!...hotel_id, 체크인날짜, 체크아웃날짜, 예약한 객실(standard,suite,deluxe)의 타입 데이터 넘길 메소드
   //딱히 매핑주소가 필요하진 않아보임?
 //hotel_id는 hotelDTO에 있고...
   //check_in 날짜와 check_out 날짜는 장바구니 테이블 basketdto의 req_date, reservation 테이블의 inq_date 컬럼 갖다 써야하는지?
   //내가 예약한 객실..room 컬럼 room == standard 뭐 이렇게 넘겨야 하는것임? 
   //테이블 안써도 되고..detail.html부분에서 체크인, 체크아웃 날짜 선택하는 부분에서 그 날짜를 value name으로 받아서 넘기면 될듯?
   
   @RequestMapping(value = "/bookRoom", method = RequestMethod.POST)
   public ModelAndView bookRoom(HttpServletRequest request, @RequestParam("hotel_id") int hotel_id) throws Exception {
	   		ModelAndView mav = new ModelAndView();
	   		
		    String checkin = request.getParameter("checkin");
		    String checkout = request.getParameter("checkout");
		    
		    mav.addObject("hotel_id", hotel_id);
		    mav.addObject("checkin", checkin);
		    mav.addObject("checkout", checkout);
			System.out.println(checkin);
			System.out.println(checkout);
			System.out.println(hotel_id);
		  
			mav.setViewName("hotel/bookingTest");   

	   return mav;
   }
   
   
   
   //이거 걍 테스트용
   @GetMapping("/roomInfo")
   public ModelAndView roomInfo(HttpServletRequest request) throws Exception {
	   
	   ModelAndView mav = new ModelAndView();
	   mav.setViewName("hotel/layoutTest");
	   
	   return mav;
   }
   
   
   
}





