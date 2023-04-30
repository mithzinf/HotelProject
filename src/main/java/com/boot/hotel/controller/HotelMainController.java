package com.boot.hotel.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.mapper.ReviewMapper;
import com.boot.hotel.service.HotelBasketService;
import com.boot.hotel.service.HotelDetailService;
import com.boot.hotel.service.HotelInfoService;
import com.boot.hotel.service.HotelMainService;
import com.boot.hotel.util.MyUtil;

@RestController
public class HotelMainController {
	
	@Autowired
    private HotelInfoService hotelInfoService;
 
    @Autowired
    private HotelBasketService hotelBasketService;
    
    @Autowired
    private HotelDetailService hotelDetailService;
    
    @Autowired
    private ReviewMapper reviewMapper;
    
    @Autowired
    private MyUtil myUtil;
	
	@Resource
	private HotelMainService hotelMainService;

	//호텔 메인페이지 진입
	@RequestMapping(value = "/hotel/main", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView main() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		int[] hotel_id = new int[8];
		for(int i=0;i<8;i++) {
			hotel_id[i] = 1 + i*6;
		}
		
		List<Map<String, Object>> mainHotel = new ArrayList<>();
		for(int i = 0; i < hotel_id.length; i++) {
		    List<Map<String, Object>> hotelData = hotelMainService.getHotelMain(hotel_id[i]);
		    
		    mainHotel.addAll(hotelData);
		}
		
		double avg = 0;
		int j = 0;
		for (Map<String, Object> map : mainHotel) {
			
			Map<String, Object> map1 = reviewMapper.searchReviewAvg(hotel_id[j]);
			
			map.put("AVG", map1.get("AVG"));
			map.put("COUNT", map1.get("COUNT"));
			j++;
			
		}
		
		//오늘,내일 날짜를 html에 보내기 위해
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	    
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());

	    // 내일 날짜로 설정
	    calendar.add(Calendar.DATE, 1);

	    String tomorrowDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		

		 
		 


		List<Map<String, Object>> reviewData = hotelDetailService.getReviewData();

		System.out.println(reviewData);

		 
		
	    mav.addObject("reviewData",reviewData);
	    
	    mav.addObject("check_in_day", currentDate);
	    mav.addObject("check_out_day", tomorrowDate);
		mav.addObject("mainHotel",mainHotel);
		mav.setViewName("hotel/hotelMain");
		
		return mav;
	}
	
	
	
	
	//메인페이지(hotelMain.html)에서 검색시 여기로 진입
	@GetMapping("/hotel/test11")
	public ModelAndView test1(@RequestParam String searchValue, @RequestParam String check_in, 
			@RequestParam String check_out) throws Exception {
		
		
		System.out.println("검색 테스트 컨트롤러 진입");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("searchValue", searchValue);
		
		LocalDate check_in_day = LocalDate.parse(check_in, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate check_out_day = LocalDate.parse(check_out, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		System.out.println("check_in_day : " + check_in_day);
		System.out.println("check_out_day : " + check_out_day);
		
		int days = (int) ChronoUnit.DAYS.between(check_in_day, check_out_day);
		
		System.out.println("days : " + days);
		
		LocalDate check_in_temp = check_in_day;
		LocalDate check_out_temp = check_in_day.plusDays(1);
		int str = 0;
		String judge = "";
		
		
		//검색값 결과를 찾는 sql문 호출하고 반환값을 result란 리스트에 저장
		//우리는 searchValue값만 입력하고, searchKey는 SQL문 안에서 임의로 지정함
		//=> 따라서 컨트롤러에 searchKey가 없다! 우린 searchKey값이 고정되어 있기 때문에..
		//검색값에 해당하는 hotel테이블의 hotel_name과 addr1을 동시에 뒤지게 됨
		List<Map<String, Object>> result = hotelMainService.searchMainHotel(paramMap);
		
		System.out.println(result);
		
		int num = 0;
		for(Map<String,Object> i: result) {
			
			int hotel_id = Integer.parseInt(i.get("HOTEL_ID").toString());
			
			for(int j=0;j<days;j++) {
				
				Map<String, Object> paramMap2 = new HashMap<>();
				paramMap2.put("check_in", check_in_temp.plusDays(j));
				paramMap2.put("check_out", check_out_temp.plusDays(j));
				paramMap2.put("hotel_id", hotel_id);
				str = hotelMainService.searchDay(paramMap2);
				if(str>14) {
					judge = "만실";
				}else if(str == 13 || str == 14) {
					judge = "만실 임박";
				}else {
					judge = "여유";
				}
				
			}
			
			if(judge.equals("만실")) {
				System.out.println(i.get("HOTEL_NAME").toString() + "는(은) 이미 만실입니다.");
				i.put("soldout", "full");
				i.put("num", 15-str);
				judge = "";
			}
			else if(judge.equals("만실 임박")){
				System.out.println(i.get("HOTEL_NAME").toString() + "는(은) 만실이 임박했습니다.");
				i.put("soldout", "almost");
				i.put("num", 15-str);
				judge = "";
			}else {
				System.out.println(i.get("HOTEL_NAME").toString() + "는(은) 아직 여유가 있습니다.");
				i.put("soldout", "empty");
				i.put("num", 15-str);
				judge = "";
			}
			num++;
			
		}
		
		
		System.out.println(result.size()); //결과값 개수 출력
		System.out.println(result); //결과값 전체 출력
		
		ModelAndView mav = new ModelAndView();

		mav.addObject("result",result); //이동할 html로 result담아서 보냄
		mav.setViewName("hotel/test"); //test.html로 이동
		
		return mav;
	}
	
	
	@RequestMapping(value = "/hotel/hotelList_test", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView list(HttpServletRequest request, @RequestParam String check_in, 
			@RequestParam String check_out)throws Exception {
		
		LocalDate check_in_day = LocalDate.parse(check_in, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate check_out_day = LocalDate.parse(check_out, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
        ModelAndView mav = new ModelAndView();

        String pageNum = request.getParameter("pageNum");

        int currentPage = 1;

        if(pageNum!=null) {
            currentPage = Integer.parseInt(pageNum);
        }

        String searchValue = request.getParameter("searchValue");


        if (searchValue == null || searchValue.equals("")) {
            searchValue = "";
        }

        if (request.getMethod().equalsIgnoreCase("GET") && searchValue != null) {
            searchValue = URLDecoder.decode(searchValue, "UTF-8");
        }

        Map<String, Object> params1 = new HashMap<>();
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
        params.put("searchValue", searchValue);
        
        
        if(!searchValue.equals("") && searchValue != null) {
            params.put("searchValue", searchValue);
        }
       
        List<HotelDTO> hotelList1 = hotelInfoService.getHotelList1(params);
        List<HotelInfoDTO> hotelList2 = hotelInfoService.getHotelList2(params);
        List<HotelPictureDTO> hotelList3 = hotelInfoService.getHotelList3(params); // 추가된 부분입니다.

        
        System.out.println("hotelList3 :" + hotelList3);
        
        String currentUrl = request.getRequestURL().toString() + "?" + request.getQueryString();
        String listUrl = currentUrl.replaceAll("&?pageNum=\\d+", "");

       

        String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl, searchValue);


        
        String detailUrl = "/detail?searchValue=" + 
                searchValue + "&pageNum=" + currentPage;

        if (searchValue != null && !searchValue.equals("")) {
            detailUrl += "&searchValue=" + searchValue;
        }

        if (pageNum != null) {
            detailUrl += "&" + "pageNum=" + pageNum;
        }

        
        mav.addObject("check_in_day",check_in_day);
        mav.addObject("check_out_day",check_out_day);
        mav.addObject("hotelList1", hotelList1);
        mav.addObject("hotelList2", hotelList2);
        mav.addObject("hotelList3", hotelList3); 
        mav.addObject("pageIndexList", pageIndexList);
        mav.addObject("dataCount", dataCount);
        mav.addObject("currentPage", currentPage);
        mav.addObject("numPerPage", numPerPage);
        mav.addObject("detailUrl", detailUrl);
        mav.addObject("searchValue", searchValue);
      

        mav.setViewName("hotel/hotellist_test");

        return mav;
    
        
    }
	
	
	
	
	
}
