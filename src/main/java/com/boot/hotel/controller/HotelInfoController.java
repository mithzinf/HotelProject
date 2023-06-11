package com.boot.hotel.controller;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.boot.hotel.dto.HotelBasketDTO;
import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.mapper.ReviewMapper;
import com.boot.hotel.oauth2.dto.SessionUser;
import com.boot.hotel.service.HotelBasketService;
import com.boot.hotel.service.HotelInfoService;
import com.boot.hotel.service.HotelMainService;
import com.boot.hotel.util.MyUtil;


@RestController
@RequestMapping("/hotel")
public class HotelInfoController {
	
	@Autowired
	private HttpSession httpSession;

    @Autowired
    private HotelInfoService hotelInfoService;
 
    @Autowired
    private HotelBasketService hotelBasketService;
    
    @Autowired
    private MyUtil myUtil;
    
    @Resource
	private HotelMainService hotelMainService;
    
    @Autowired
    private ReviewMapper reviewMapper;
    
    
    @RequestMapping(value = "/hotellist", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView list(HttpServletRequest request, @RequestParam String check_in, 
			@RequestParam String check_out)throws Exception {
  
    	//체크인, 체크아웃 시간을 받아 알맞은 형태로 바꾸고 변수에 저장
    	LocalDate check_in_day = LocalDate.parse(check_in, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate check_out_day = LocalDate.parse(check_out, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		//카테고리 검색일때를 위해
		String category = request.getParameter("category");
		
		//숙박일수 계산
		int days = (int) ChronoUnit.DAYS.between(check_in_day, check_out_day);
		
		//아래 날짜에 따른 만실 체크를 위해 미리 준비
		LocalDate check_in_temp = check_in_day;
		LocalDate check_out_temp = check_in_day.plusDays(1);
		int str = 0;
		int str2 = 0;
		String judge = "";
		//준비끝
		
		
        ModelAndView mav = new ModelAndView();

        String pageNum = request.getParameter("pageNum");

        int currentPage = 1;

        if(pageNum!=null) {
            currentPage = Integer.parseInt(pageNum);
        }
        
        
        //카테고리 검색이면 searchValue를 넘어온 카테고리로 저장
        String searchValue = "";
        
        if(category == null || category.equals("")) {
        	searchValue = request.getParameter("searchValue");
        }else {
        	searchValue = category;
        }


        if (searchValue == null || searchValue.equals("")) {
            searchValue = "";
        }

        if (request.getMethod().equalsIgnoreCase("GET") && searchValue != null) {
            searchValue = URLDecoder.decode(searchValue, "UTF-8");
        }

        Map<String, Object> params1 = new HashMap<>();
        params1.put("searchValue", searchValue);
        
        
        //카테고리 검색과 일반 검색을 구분해 전체 개수 구하기
        int dataCount = 0;
        
        if(category == null || category.equals("")) {
        	dataCount = hotelInfoService.getHotelCount(params1);
        }else {
        	dataCount = hotelInfoService.getHotelCountCategory(params1);
        }
        
        
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
   
        
        //카테고리 검색과 일반 검색을 구분해 리스트 구하기
        List<HotelDTO> hotelList1;
        
        if(category == null || category.equals("")) {
        	hotelList1 = hotelInfoService.getHotelList1(params);
        }else {
        	hotelList1 = hotelInfoService.getHotelList1Category(params);
        }

        
        
        List<HotelInfoDTO> hotelList2 = hotelInfoService.getHotelList2(params);
        List<HotelPictureDTO> hotelList3 = hotelInfoService.getHotelList3(params); // 추가된 부분입니다.
        
        
        //날짜계산, 찜 목록 체크, 리뷰 평점 추가 시작
        int num = 0;
        double avg = 0;
		int k = 0;
		
        for(HotelDTO i : hotelList1) {
        	
    		int hotel_id =(i.getHotel_id());
    		
    		//체크인~체크아웃 날짜를 하루씩 판별하여 만실인지 체크함
    		//3박4일이면 3번 반복
    		for(int j=0;j<days;j++) {
    			
				Map<String, Object> paramMap2 = new HashMap<>();
				paramMap2.put("check_in", check_in_temp.plusDays(j));
				paramMap2.put("check_out", check_out_temp.plusDays(j));
				paramMap2.put("hotel_id", hotel_id);
				str = hotelMainService.searchDay(paramMap2);
				if(str>14) {
					judge = "만실";
					break;
				}else if(str == 13 || str == 14) {
					judge = "만실 임박";
					str2 = 2;
					if(str==14) {
						str2 = 1;
					}
				}else {
					if(judge!="만실 임박") {
						judge = "여유";
					}
				}
			
    		}
			
    		//만실 체크
			if(judge.equals("만실")) {
				i.setSoldout("만실");
				judge = "";
			}
			else if(judge.equals("만실 임박")){
				i.setSoldout("만실 임박");
				i.setNum(str2);
				judge = "";
			}else {
				i.setSoldout("여유");
				judge = "";
			}
			
			
			SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
	    	String userid = "";
			if(sessionUser!=null) {
				userid = sessionUser.getId();
			}
	    	
			
			//찜 유무 체크
			Map<String, Object> paramMap3 = new HashMap<>();
			paramMap3.put("userid", userid);
			paramMap3.put("hotel_id", hotel_id);
			
			String basket_judge = "";
			
			basket_judge = hotelInfoService.searchListBasket(paramMap3);
			
			if (basket_judge == null || basket_judge.equals("")) {
			    i.setBasket_(false);
			} else {
			    i.setBasket_(true);
			}
			
			
			//평점 체크
			Map<String, Object> map1 = reviewMapper.searchReviewAvg(hotel_id);
			
			i.setAvg(((BigDecimal) map1.get("AVG")).doubleValue());
			i.setCount(((BigDecimal) map1.get("COUNT")).intValue());
			num++;

        }
        //날짜계산, 찜 유무 체크 끝
        
        
        //리스트, 검색, 페이징 준비
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
        
        if(httpSession.getAttribute("sessionUser")!=null) {
        	SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        	mav.addObject("user_id",sessionUser.getId());
        }
        
        mav.setViewName("hotel/hotelList");

        return mav;
    
    
        
    }
    
    //리스트에서 찜 추가 처리 메소드
    @PostMapping("/addBasket")
    public String addBasket(@RequestParam int hotel_id) throws Exception {
    	
    	System.out.println("찜 추가 컨트롤러 진입");
    	
    	SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
    	String userid = sessionUser.getId();
        
        HotelBasketDTO dto = new HotelBasketDTO();

        int maxNum = hotelBasketService.maxNum();
        dto.setBasket_num(maxNum + 1);
        dto.setUserid(userid);
        dto.setHotel_id(hotel_id);
        
        System.out.println("userid :  " + userid);
        System.out.println("hotel_id : " + hotel_id);
        
        hotelBasketService.addHotelBasket(dto);
        
        return "추가 성공";
    }

    
    //리스트에서 찜 해제 처리 메소드
    @PostMapping("/removeBasket")
    public String removeBasket(@RequestParam int hotel_id) throws Exception {
    	
    	System.out.println("찜 해제 컨트롤러 진입");
    	
    	SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
    	String userid = sessionUser.getId();
    
     	HotelBasketDTO dto = new HotelBasketDTO();
     	dto.setUserid(userid);
     	dto.setHotel_id(hotel_id);
     	
     	hotelBasketService.deleteHotelBasket(dto);
     	
        return "삭제 성공";
         
    }
    
}





















