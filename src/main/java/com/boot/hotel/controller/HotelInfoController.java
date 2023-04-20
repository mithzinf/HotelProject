package com.boot.hotel.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.service.HotelImageService;
import com.boot.hotel.service.HotelInfoService;
import com.boot.hotel.util.MyUtil;


@RestController
@RequestMapping("/hotel")
public class HotelInfoController {

    @Autowired
    private HotelInfoService hotelInfoService;
    
    @Autowired
    private MyUtil myUtil;

    
    @RequestMapping(value = "/hotellist", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView list(HttpServletRequest request)throws Exception {

        ModelAndView mav = new ModelAndView();

        String pageNum = request.getParameter("pageNum");

        int currentPage = 1;

        if(pageNum!=null) {
            currentPage = Integer.parseInt(pageNum);
        }

        String searchKey = request.getParameter("searchKey");
        String searchValue = request.getParameter("searchValue");

        if (searchKey == null || searchValue.equals("")) {
            searchKey = "hotel_name";
            searchValue = "";
        }

        if (searchValue != null) {
            if(request.getMethod().equalsIgnoreCase("GET")){
                searchValue = URLDecoder.decode(searchValue, "UTF-8");
            }
        }

        Map<String, Object> params1 = new HashMap<>();
        params1.put("searchKey", searchKey);
        params1.put("searchValue", searchValue);

        int dataCount = hotelInfoService.getHotelCount(params1);

        int numPerPage = 10;

        int totalPage = myUtil.getPageCount(numPerPage, dataCount);

        if (currentPage>totalPage) {
            currentPage = totalPage;
        }

        int start = (currentPage-1)*numPerPage+1;
        int end = currentPage*numPerPage;

        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);
        params.put("searchKey", searchKey);
        params.put("searchValue", searchValue);

        if(!searchValue.equals("") && searchValue != null) {
            params.put("searchKey", searchKey);
            params.put("searchValue", searchValue);
        } else {
            params.put("searchKey", "hotel_name");
            params.put("searchKey", "addr1");
            params.put("searchKey", "addr2");
            params.put("searchValue", "");
        }

        List<HotelDTO> hotelList1 = hotelInfoService.getHotelList1(params);
        List<HotelInfoDTO> hotelList2 = hotelInfoService.getHotelList2(params);
        List<HotelPictureDTO> hotelList3 = hotelInfoService.getHotelList3(params); // 추가된 부분입니다.

        
        System.out.println("hotelList3 :" + hotelList3);
        
        
        String listUrl = "";

        String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);

        String detailUrl = "/detail?pageNum=" + currentPage;

        mav.addObject("hotelList1", hotelList1);
        mav.addObject("hotelList2", hotelList2);
        mav.addObject("hotelList3", hotelList3); // 추가된 부분입니다.
        mav.addObject("pageIndexList", pageIndexList);
        mav.addObject("dataCount", dataCount);
        mav.addObject("currentPage", currentPage);
        mav.addObject("numPerPage", numPerPage);
        mav.addObject("detailUrl", detailUrl);

        mav.setViewName("hotel/hotelList");

        return mav;
    }

   
}
