package com.boot.hotel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.service.HotelImageService;

@RestController
@ResponseBody
public class HotelImageController {
	
	@Resource
	private HotelImageService hotelImageService;
	
	//이미지 업로드 테스트
	@RequestMapping(value = "/hotel/imageTest", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView imageTest() throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		//2번 파일 이름을 컨트롤러에서 지정해보기
		String test = "2_standard.jpg";
		
		//3번
		//4번호텔의 스위트룸 이미지들을 컨트롤러에서 받아와 띄우기
		//hotel_picture 테이블에 hotel_id와 type(룸 종류)칼럼으로
		//where문을 줘서 원하는 값을 select해오면 됨
		//매개변수는 여러개를 넣어야 하니 Map으로,
		//반환값은 여러개 이미지가 올수도 있으니 list로 받는다
		//주의할점은 xml mapper코드에서 반환값이 list일경우엔
		//resultType을 map으로 받아줘야 함!!
		Map<String, Object> paramMap = new HashMap<>();
		int hotel_id = 4;
		String type = "sweet";
		paramMap.put("hotel_id", hotel_id);
		paramMap.put("type", type);
		
		//여기서 반환값을 List<String> -> List<Map<String, Object>>으로 변경
		//당연히 서비스, impl, mapper.java도 바꿔줘야 함 xml은 변경 ㄴㄴ
		List<Map<String, Object>> detailSweet = hotelImageService.searchDetailSweet(paramMap);
		
		System.out.println(detailSweet.size());
		System.out.println(detailSweet); //데이터 콘솔에 출력해보기
		
		//결과값의 칼럼 하나를 받아줄 배열 생성
		String[] urlname = new String[detailSweet.size()];
		int i = 0;
		
		//확장for문으로 돌리면서 배열갑 저장
    	for (Map<String,Object> url: detailSweet ){
            urlname[i] = String.valueOf(url.get("URL"));
            System.out.println(urlname[i]); // 전체값이 아닌 URL이란 key값만 뽑아서 출력해보기
            i++;
        }
    	
    	
		
		
		mav.addObject("test",test);
		mav.addObject("detailSweet",detailSweet);
		mav.setViewName("hotel/imageTest");
		//html은 templates.hotel의 imageTest.html
		
		return mav;
		
	}
	
	
	@GetMapping("/hotel/main1")
	public ModelAndView test() throws Exception {
		
		
		
		
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("hotel/hotelMain");
		
		return mav;
	}
	
	@RequestMapping(value = "/nav/index", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView navtest(HttpServletRequest request) throws Exception {

		ModelAndView  mav = new ModelAndView();
		
		mav.setViewName("layout/index");
		
		return mav;
	}
	
}
