package com.boot.hotel.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.service.HotelDetailService;

@RestController
public class HotelDetailController {
   
   @Resource
   private HotelDetailService hotelDetailService;

   @GetMapping("/detail")
   public ModelAndView detail(HttpServletRequest request) throws Exception {
      System.out.println("들어왓니? 폼에 담아서 보내라구요?우헤헤..");
      int hotel_id = Integer.parseInt(request.getParameter("hotel_id"));
      System.out.println(hotel_id);
      HotelInfoDTO dto = hotelDetailService.getListsDetailData(hotel_id);
      
      
      ModelAndView mav = new ModelAndView();
      //mav.addObject("hotel_id",hotel_id);
      mav.setViewName("/login/detail"); //templates.login의 detail.html로 가게 하려고...
      
      return mav;
      
   }
   
}