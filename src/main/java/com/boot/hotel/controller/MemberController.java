package com.boot.hotel.controller;




import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.MemberDTO;
import com.boot.hotel.service.MemberService;

@RestController
@ResponseBody
public class MemberController {
	
	@Resource
	private MemberService memberService;
	
	//메인화면 띄우기
	@GetMapping("/")
	public ModelAndView index() throws Exception{
		
		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("index");
		
		return mav;
		
	}
	
	//로그인창 띄우기
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login() throws Exception {
	    System.out.println("로그인창 띄우기");
	    // 반환값은 MVC로
	    ModelAndView mav = new ModelAndView();

	    mav.setViewName("login/login");

	    return mav;
	}
	
	//약관동의화면 띄우기
	@GetMapping("/login/terms")
	public ModelAndView terms() throws Exception{
		System.out.println("컨트롤러에서 약관동의 화면 띄우기");
		
		ModelAndView mav = new ModelAndView();
			
		mav.setViewName("login/terms");
			
		return mav;
			
	}
	
	//연동/일반 회원가입 선택 화면 띄우기
	@GetMapping("/login/select")
	public ModelAndView select() throws Exception{
		System.out.println("연동/일반 회원가입 선택 창 띄우기");
		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/select");
		
		return mav;
		
	}
	
	
	
	//회원가입화면 띄우기
	@RequestMapping(value = "/login/register", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView register() throws Exception{
		System.out.println("회원가입창 띄우기");
		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/register");
		
		return mav;
		
	}
	
	
	//회원가입 처리
	@PostMapping("/login/register_ok")
	public ModelAndView register_ok(MemberDTO dto) throws Exception{
		//반환값은 MVC로
		System.out.println("회원가입 컨트롤러 진입");
		ModelAndView mav = new ModelAndView();
		
		int maxNum = memberService.maxNum();
		dto.setNum(maxNum + 1);
		
		memberService.insertDataMember(dto);
		
		System.out.println("회원가입 성공!!");
		
		mav.setViewName("redirect:/login");
		
		return mav;
		
	}

	
	//아이디 중복체크 새창 띄우기
	@GetMapping("/login/idcheck1")
	public ModelAndView register11() throws Exception{
		System.out.println("중복체크 창 띄우기");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/idcheck");
		
		return mav;
		
	}
	
	
	//아이디 중복체크 처리
	@PostMapping("/login/check")
	public String idcheck(@RequestParam String id) throws Exception{
		
		System.out.println("중복체크 확인 컨트롤러 진입");
		
		String result = memberService.checkMemberId(id);
		
		if(result!=null) {
			return id;
		}
		
		return "available";
		
	}
	
	
	//회원가입 창에서 뒤로가기 버튼 눌렀을 때 로그인 화면으로 리다이렉트
	@PostMapping("/login/back")
	public ModelAndView back() throws Exception{

		System.out.println("로그인 화면으로 되돌아갑니다.");
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/login");
		
		return mav;
		
	}
	
	//회원가입시 전화번호 체크
	@PostMapping("/login/emailCheck")
	public String emailCheck(@RequestParam("tel1") String tel1,
			@RequestParam("tel2") String tel2, @RequestParam("tel3") String tel3) throws Exception{

		System.out.println("이메일 체크 컨트롤러 진입");
		
		StringBuilder sb = new StringBuilder();
		
		String tel = sb.append(tel1).append("-").append(tel2).append("-").append(tel3).toString();
		
		String result = memberService.checkMemberTel(tel);
		
		if(result!=null) {
			return "available";
		}
		
		return tel;
		
			
	}
	
	//아이디 찾기 페이지 진입
	@GetMapping("/login/searchId")
	public ModelAndView searchId() throws Exception{

		System.out.println("컨트롤러 아이디 찾기 페이지 진입");
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/searchId");
		
		return mav;
		
	}
	
	//아이디 찾기 처리
	@PostMapping("/login/useridSearch")
	public String useridSearch(@RequestParam("username") String username, @RequestParam("tel1") String tel1,
			@RequestParam("tel2") String tel2, @RequestParam("tel3") String tel3) throws Exception{

		System.out.println("아이디 찾기 컨트롤러 진입");
		
		StringBuilder sb = new StringBuilder();
		
		String tel = sb.append(tel1).append("-").append(tel2).append("-").append(tel3).toString();
	
		String id = "";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", username);
		paramMap.put("tel", tel);
		
		id = memberService.searchMemberId(paramMap);
		
		return id;
				
	}
	
	
	
	
	//비밀번호 찾기 페이지 진입
	@GetMapping("/login/searchPwd")
	public ModelAndView searchPwd() throws Exception{
			
		System.out.println("컨트롤러 비밀번호 찾기 페이지 진입");
		
		ModelAndView mav = new ModelAndView();
			
		mav.setViewName("login/searchPwd");
			
		return mav;
			
	}
	
	//비밀번호 찾기 처리
	@PostMapping("/login/pwdSearch")
	public String pwdSearch(@RequestParam("userid") String userid,
		@RequestParam("username") String username, @RequestParam("tel1") String tel1,
		@RequestParam("tel2") String tel2, @RequestParam("tel3") String tel3) throws Exception{
			
		System.out.println("비밀번호 찾기 컨트롤러 진입");
			
		StringBuilder sb = new StringBuilder();
			
		String tel = sb.append(tel1).append("-").append(tel2).append("-").append(tel3).toString();
		
		String pwd = "";
			
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userid", userid);
		paramMap.put("username", username);
		paramMap.put("tel", tel);
			
		pwd = memberService.searchMemberPwd(paramMap);
			
		return pwd;
					
	}
	
	
	
	
	//=======================================================================================
	
	
	//임시 로그인 페이지 list 띄움
	@PostMapping("/login/list")
	public ModelAndView list(MemberDTO dto) throws Exception{

		System.out.println("임시 로그인 페이지 진입");
		
		//회원 전체 데이터 가져옴
		MemberDTO dto1 = memberService.getReadDataMember(dto.getUserid());
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("dto", dto1);
		mav.setViewName("login/list");
		
		return mav;
		
	}
	
	//임시 로그인 페이지 list_ok 띄움
	@PostMapping("/login/list_ok")
	public ModelAndView list_ok(MemberDTO dto) throws Exception{

		System.out.println("list_ok 컨트롤러 진입");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		mav.setViewName("login/list_ok");
		
		return mav;
		
	}
	
	
	
}
