package com.boot.hotel.controller;
import java.util.HashMap;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.hotel.dto.MemberDTO;
import com.boot.hotel.oauth2.dto.SessionUser;
import com.boot.hotel.service.MemberService;

@RestController
@ResponseBody
public class MemberController {
	
	//oauth2 회원가입 정보 입력을 위해
	private String oauthId;
	private String oauthName;
	
	@Autowired
	private HttpSession httpSession;
	
	@Resource
	private MemberService memberService;

	
	//메인화면 띄우기
	@GetMapping("/")
	public ModelAndView index() throws Exception{
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("index");
		
		return mav;
		
	}
	
	//로그인창 띄우기
	@RequestMapping(value = "/login/login", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login() throws Exception {
	    System.out.println("로그인창 띄우기");
	    
	    ModelAndView mav = new ModelAndView();

	    mav.setViewName("login/login");
	    httpSession.invalidate();
	    this.oauthId = "";
	    this.oauthName = "";
	    
	    return mav;
	}
	
	//로그인시 맞는 아이디/비밀번호인지 확인
	@PostMapping("/login/login_check")
	public String login_check(@RequestParam String userid,
			@RequestParam String pwd) throws Exception {
		
		System.out.println("로그인 체크 컨트롤러 진입");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userid", userid);
		paramMap.put("pwd", pwd);
		System.out.println(userid);
		if(memberService.memberLogin(paramMap)!=null) {
			return "success";
		}
		
		return null;
		
	}
	
	
	
	//아이디 비밀번호가 맞을시 일반 로그인 처리
	@RequestMapping(value = "/login/login_ok", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login_ok(MemberDTO dto) throws Exception {
	    System.out.println("일반 로그인 처리 컨트롤러 진입");
	    
	    ModelAndView mav = new ModelAndView();
	    
	    Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userid", dto.getUserid());
		paramMap.put("pwd", dto.getPwd());
		
		MemberDTO dto2 = memberService.memberLogin(paramMap);
			
		SessionUser sessionUser = new SessionUser(dto2.getUserid(),dto2.getUsername());
		httpSession.setAttribute("sessionUser", sessionUser);
		    
		System.out.println();

		mav.setViewName("redirect:/list");
		    
		return mav;
		
	}
	
	//연동/일반 회원가입 선택 화면 띄우기
	@GetMapping("/login/register_select")
	public ModelAndView select() throws Exception{
		System.out.println("연동/일반 회원가입 선택 창 띄우기");
		//반환값은 MVC로
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/register_select");
		
		return mav;
		
	}
	
	//약관동의화면 띄우기
	@RequestMapping(value = "/login/terms", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView terms() throws Exception{
		System.out.println("컨트롤러에서 약관동의 화면 띄우기");
		
		ModelAndView mav = new ModelAndView();
			
		mav.setViewName("login/terms");
			
		return mav;
			
	}
	
	
	//oauth 로그인시 가입된 계정인지 아닌지 판별하는 메소드
	@RequestMapping(value = "/login/oauth_select", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView oauth_select() throws Exception{
		System.out.println("oauth로그인시 가입된 계정인지 판별 컨트롤러 진입");
		
		//세션 값 가져옴
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		String id = "";
		
		//가져온 세션값의 아이디가 등록된 아이디인지 판별
		id = memberService.checkMemberId(sessionUser.getId());
		System.out.println(id);
		
		
		//미가입된 계정이면 회원가입 페이지로
		if(id==null) {
			
			ModelAndView mav = new ModelAndView();
			
			mav.setViewName("redirect:/login/terms");
			
			return mav;
			
		}
		
		//가입된 계정이면 로그인 화면으로
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/list");
		
		return mav;
		
	}
	
	
	
	
	
	//회원가입화면 띄우기
	@RequestMapping(value = "/login/register", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView register() throws Exception{
		System.out.println("회원가입창 띄우기");
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		if(sessionUser!=null) {
			
			this.oauthId = sessionUser.getId();
			this.oauthName = sessionUser.getName();
			httpSession.invalidate();
			
			ModelAndView mav = new ModelAndView();
			
			mav.addObject("oauthId", oauthId);
	        mav.addObject("oauthName", oauthName);
			mav.setViewName("login/register_oauth");
			
			return mav;
			
		}
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/register");
		
		return mav;
		
	}
	
	
	
	
	//회원가입 처리
	@PostMapping("/login/register_ok")
	public ModelAndView register_ok(MemberDTO dto) throws Exception{

		System.out.println("회원가입 처리 컨트롤러 진입");
		ModelAndView mav = new ModelAndView();
		
		if(dto.getAuth()!=null) {
			
			int maxNum = memberService.maxNum();
			dto.setNum(maxNum + 1);
			dto.setAuth("소셜회원");
			
			String[] parts = dto.getEmail().split("@"); // "@" 기호를 기준으로 문자열을 분리

			dto.setEmail1(parts[0]);
			dto.setEmail2(parts[1]);
			
			memberService.insertDataSocialMember(dto);
			
			System.out.println("oauth 회원가입 성공!!");
			
			mav.setViewName("redirect:/login/login");
			
			return mav;
			
		}
		
		
		int maxNum = memberService.maxNum();
		dto.setNum(maxNum + 1);
		dto.setAuth("일반회원");
		
		memberService.insertDataMember(dto);
		
		System.out.println("일반 회원가입 성공!!");
		
		mav.setViewName("redirect:/login/login");
		
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
		
		mav.setViewName("redirect:/login/login");
		
		return mav;
		
	}
	
	//회원가입시 전화번호 체크
	@PostMapping("/login/telCheck")
	public String emailCheck(@RequestParam("tel1") String tel1,
			@RequestParam("tel2") String tel2, @RequestParam("tel3") String tel3) throws Exception{

		System.out.println("회원가입시 전화번호 체크 컨트롤러 진입");
		
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
	
	
	//로그아웃 처리
	@RequestMapping(value = "/login/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView logout() throws Exception{
		
		//로그인할때 올린 세션을 삭제
		httpSession.invalidate();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/list");
		
		return mav;
		
	}
	
	
	
	//=======================================================================================
	
	
	//임시 로그인 페이지 list 띄움
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView list() throws Exception{

		System.out.println("리스트 출력 컨트롤러 진입");
		
		ModelAndView mav = new ModelAndView();
		
		if(httpSession.getAttribute("sessionUser")!=null) {
			
			SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
			
			String userid = sessionUser.getId();
			String username = sessionUser.getName();
			
			mav.addObject("userid", userid);
			mav.addObject("username", username);
			
		}
		
		
		mav.setViewName("login/list");
		
		return mav;
		
	}
	
	
	

	
}
