package com.boot.hotel.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelFacilityInDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.service.HotelDetailService;

@RestController
public class HotelDetailController {
   
   @Resource
   private HotelDetailService hotelDetailService;

   @GetMapping("/detail")
   public ModelAndView detail(HttpServletRequest request) throws Exception {
      System.out.println("들어왓니? 폼에 담아서 보내라구요?우헤헤..");
     
      int hotel_id = Integer.parseInt(request.getParameter("hotel_id"));
     //질문 : 이걸 써야하는지 잘 모르겠음 ㅎㅎ
    //  String pageNum = request.getParameter("pageNum");
      
      //hotel_id가 흘러들어왔는지 확인하는 syso문
      System.out.println(hotel_id);
      
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
     
      //mav.addObject("hotel_id",hotel_id);
      mav.setViewName("login/detail"); //templates.login의 detail.html로 가게 하려고...
      
      return mav;
      
   }
   
}





