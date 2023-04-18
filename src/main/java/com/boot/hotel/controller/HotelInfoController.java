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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.HotelDTO;
import com.boot.hotel.dto.HotelInfoDTO;
import com.boot.hotel.dto.HotelPictureDTO;
import com.boot.hotel.dto.ReviewDTO;
import com.boot.hotel.dto.ReviewScoreDTO;
import com.boot.hotel.service.HotelInfoService;
import com.boot.hotel.util.MyUtil;


@RestController
public class HotelInfoController {

    @Resource
    private HotelInfoService hotelInfoService;

    @Autowired
    private MyUtil myUtil;

    @GetMapping("/hotel/hotellist")
    public ModelAndView list(HttpServletRequest request, @RequestParam(value="pageNum", defaultValue="1") int pageNum) throws Exception {
            
        ModelAndView mav = new ModelAndView();
            
        int numPerPage = 5; // 한 페이지에 출력할 데이터 개수
        int totalCount = hotelInfoService.getHotelCount(); // 전체 데이터 개수
        int pageCount = myUtil.getPageCount(numPerPage, totalCount); // 전체 페이지 개수
   
        
        
        // pageNum 값이 잘못된 경우 1로 설정
        if(pageNum < 1 || pageNum > pageCount) {
            pageNum = 1;
        }
            
        int start = (pageNum - 1) * numPerPage; // 현재 페이지 시작 행 번호
        int end = pageNum * numPerPage; // 현재 페이지 끝 행 번호
        int pageBlock = 5; // 페이지 블록 개수
        int currentPageSetup = (pageNum - 1) / pageBlock * pageBlock; // 현재 페이지 블록 시작 페이지 번호
        int currentPageBlock = currentPageSetup / pageBlock + 1; // 현재 페이지 블록 번호

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("start", start);
        paramMap.put("end", end);

        List<Map<String, Object>> hotelList1 = hotelInfoService.getHotelList1(paramMap);
        List<Map<String, Object>> hotelList2 = hotelInfoService.getHotelList2(paramMap);
        List<Map<String, Object>> hotelList3 = hotelInfoService.getHotelList3(paramMap);

        mav.addObject("hotelList1", hotelList1);
        mav.addObject("hotelList2", hotelList2);
        mav.addObject("hotelList3", hotelList3);

        mav.addObject("totalCount", totalCount);
        mav.addObject("pageNum", pageNum);
        mav.addObject("pageCount", pageCount);
        mav.addObject("pageBlock", pageBlock);
        mav.addObject("currentPageBlock", currentPageBlock);
        mav.addObject("currentPageSetup", currentPageSetup);
        mav.addObject("listUrl", "hotellist");
        
        mav.setViewName("hotel/hotelList");

        return mav;
    }


}

/*
@RestController
public class HotelInfoController {
	
	@Resource
	private HotelInfoService hotelInfoService;

	@Autowired
	MyUtil myUtil = new MyUtil();

	@GetMapping("/hotel/hotellist")
	public ModelAndView list(HttpServletRequest request) throws Exception{
	    List<HotelDTO> resultList1 = hotelInfoService.getHotelList1();
	    List<HotelInfoDTO> resultList2 = hotelInfoService.getHotelList2();
	    List<HotelPictureDTO> resultList3 = hotelInfoService.getHotelList3();
//	    List<ReviewDTO> resultList4 = hotelInfoService.getHotelList4();
//	    List<ReviewScoreDTO> resultList5 = hotelInfoService.getHotelList5();

	    ModelAndView mav = new ModelAndView();
	    mav.addObject("resultList1", resultList1);
	    mav.addObject("resultList2", resultList2);
	    mav.addObject("resultList3", resultList3);
//	    mav.addObject("resultList4", resultList4);
//	    mav.addObject("resultList5", resultList5);

	    
	    
	    
        //가.넘어오는 페이지 번호 받기
        String pageNum = request.getParameter("pageNum");
        
        
        //ㄱ.list.jsp를 처음 실행하면 null일테니 처음 페이지를 인위적으로 1로 고정시켜준다
        int currentPage = 1;
        
        //ㄴ.pagenum이 null이 아니라면 그 pagenum을 숫자로 변환하여 currentpage에 덧씌워준다
        if(pageNum!=null) {
           currentPage = Integer.parseInt(pageNum);
        }
        
        
        String searchKey = request.getParameter("searchKey");
        String searchValue = request.getParameter("searchValue");
        
        if(searchValue==null) { 
           searchKey = "subject";
           searchValue = "";
        }else { //ㄹ. 검색칸에 먼가를 적긴했단겨
           if(request.getMethod().equalsIgnoreCase("GET")) {
              searchValue = URLDecoder.decode(searchValue, "UTF-8");
           }
        }
        
        //ㅁ.전체 데이터 갯수(dao.file의 int getDataCount()) 구하기
        int dataCount = HotelInfoService.getDataCount(searchKey, searchValue);
        //ㅂ.검색을 했다면, 검색한 글의 갯수, 검색을 안했다면 모든 글의 갯수 나오게 코드
        
        //총  페이지 갯수 : 10개
        //ㅅ. 한 개의 페이지에 띄울 데이터(글 제목)의 갯수 
        int numPerPage = 5;
        int totalPage = myUtil.getPageCount(numPerPage, dataCount);
        
        //ㅇ. 삭제로 인해 전체 페이지 수(tp)보다 표시할 페이지의 수(cp)가 큰 경우가 될 때가 있음
        if(currentPage>totalPage) {
           currentPage = totalPage;
         }
        
        //ㅈ. dao.getLists(1,3);에 채워넣을
        //rownum의 가져올 데이터의 시작 start과 끝 end 값 가져오기
        //numPerPage : 3 (한 페이지에 띄울 글 제목의 갯수)
        int start = (currentPage-1)*numPerPage+1;
        int end = currentPage*numPerPage;
        
        List<BoardDTO> lists = boardService.getLists(start, end, searchKey, searchValue);
        
        //ㅊ. 페이징 처리
        //(검색[searchValue의 값이 null이 아닐 때]을 했을때
        //검색을 하지않았을 때 조건문 추가)
        
        String param = "";
        if(searchValue!=null&&!searchValue.equals("")) {
           param = "searchKey=" + searchKey;
           param+="&searchValue=" + URLEncoder.encode(searchValue,"UTF-8");
        }
        //ㅋ.짭주소 적어서 listUrl에 담아주고, 검색결과를 짭주소끝에 붙일 코드
        String listUrl = "/list.action";
        if(!param.equals("")) {
           
           listUrl += "?" + param;
        }
        String pageIndexList =
              myUtil.pageIndexList(currentPage, totalPage, listUrl);
        
        
        //ㅌ. 현재의 페이지 넘버 주소 띄워줄 코드 
        String articleUrl = "/article.action?pageNum=" + currentPage;
        
        if(!param.equals("")) {
           articleUrl += "&" + param;
        }
        

        ModelAndView mav = new ModelAndView();
        
        //찐막 : 포워딩하여 list.jsp로 갖구갈 데이터를 하나하나 setAttribute로 보내준다
        mav.addObject("lists", lists);
        mav.addObject("pageIndexList", pageIndexList);
        mav.addObject("dataCount", dataCount);
        mav.addObject("articleUrl", articleUrl);
        mav.addObject("pageNum", currentPage);
     
     
	    mav.setViewName("hotel/hotelList");
	    return mav;
     
	}
	
	}
}
	
	// 검색 시 hotel_id를 가지고 호텔에 관한 리스트를 출력할 것
//	    @GetMapping("/hotelSearchList")
//	    public ModelAndView getHotelSearchList(@RequestParam String searchKey, @RequestParam String searchValue) {
//	        List<Object> resultList = hotelInfoService.getLists(searchKey, searchValue);
//	        
//	        ModelAndView mav = new ModelAndView();
//	        mav.setViewName("hotel/hotelSearchList");
//	        mav.addObject("resultList", resultList);
//	        
//	        return mav;
//	    }
//	    

*/
