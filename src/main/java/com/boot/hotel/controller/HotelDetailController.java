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
   public ModelAndView detail(@RequestParam(value = "hotel_id", required = true) int hotel_id, 
			@RequestParam(value = "type", required = true) String type) throws Exception {
      
     
      
      Map<String, Object> paramMap = new HashMap<>();
      paramMap.put("hotel_id", hotel_id);
      paramMap.put("type", type);
  
     
      
      List<HotelDTO> dto1 = hotelDetailService.getHotelById(hotel_id);
      List<HotelInfoDTO> dto2 = hotelDetailService.getHotelInfoById(hotel_id);
      List<HotelPictureDTO> dto3 = hotelDetailService.getHotelPicById(hotel_id);
      List<HotelFacilityInDTO> dto4 = hotelDetailService.getHotelFacilityInById(hotel_id);
      List<Map<String,Object>> searchHotelDetail = hotelDetailService.searchHotelDetail(paramMap);
      
      String[] urlname = new String[searchHotelDetail.size()];
      int i = 0;
      
      
      
      for (Map<String,Object> url: searchHotelDetail) {
          urlname[i] = String.valueOf(url.get("URL")); 
          System.out.println(urlname[i]); 
          i++;
      } 
      
      ModelAndView mav = new ModelAndView();
      
      mav.addObject("dto1", dto1);
      mav.addObject("dto2", dto2);
      mav.addObject("dto3", dto3);
      mav.addObject("dto4", dto4);
      mav.addObject("searchHotelDetail", searchHotelDetail);
      mav.addObject("urlname", urlname);
      mav.setViewName("hotel/detail");
      
      return mav;
   }
   
   //객실 성질에 따른 사진 보여줄 roomInfo.html 띄울 매핑 & ModelAndView 만든다..아휴 손 많이 가..
   @GetMapping("/roomInfo")
   public ModelAndView roomInfo(HttpServletRequest request) throws Exception {
	   
	   ModelAndView mav = new ModelAndView();
	   mav.setViewName("hotel/roomInfo");
	   
	   return mav;
   }
}





