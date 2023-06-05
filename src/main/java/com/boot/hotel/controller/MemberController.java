package com.boot.hotel.controller;
import java.util.HashMap;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
	
	//세션 주입
	//Autowired로 주입받아 다른 컨트롤러에도 세션을 쓰게함
	@Autowired
	private HttpSession httpSession;
	
	//서비스와 연결
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
	    //로그인이 안된 상태여야 하므로 이 페이지에 진입시 세션을 초기화
	    httpSession.invalidate();
	    
	    return mav;
	}
	
	
	//로그인시 맞는 아이디/비밀번호인지 확인
	@PostMapping("/login/login_check")
	public String login_check(@RequestParam String userid,
			@RequestParam String pwd) throws Exception {
		
		System.out.println("로그인 체크 컨트롤러 진입");
		
		//2개 이상의 매개변수를 넣기 위해 맵퍼로 담음
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userid", userid);
		paramMap.put("pwd", pwd);

		//아이디, 비밀번호가 맞다면 ajax로 성공을 반환
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
	    
	    //로그인한 사용자의 아이디, 이름을 출력
	    Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userid", dto.getUserid());
		paramMap.put("pwd", dto.getPwd());
		
		MemberDTO dto2 = memberService.memberLogin(paramMap);
			
		//출력한 값을 세션에 등록
		SessionUser sessionUser = new SessionUser(dto2.getUserid(),dto2.getUsername());
		httpSession.setAttribute("sessionUser", sessionUser);

		mav.setViewName("redirect:/hotel/main");
		    
		return mav;
		
	}
	
	//연동/일반 회원가입 선택 화면 띄우기
	@GetMapping("/login/register_select")
	public ModelAndView select() throws Exception{
		System.out.println("연동/일반 회원가입 선택 창 띄우기");
		
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
		
		//미가입된 계정이면 회원가입 페이지로
		if(id==null) {
			
			String oauthid = sessionUser.getId();
			String oauthname = sessionUser.getName();
			
			//기존 세션 삭제
			httpSession.invalidate();
			
			//회원가입 전용 세션 객체를 생성후 등록, 로그인 세션과 다름
			//그렇지 않으면 회원가입 도중 메인으로 넘어가도 로그인 상태가 되기 때문에
			SessionUser sessionUserRegister = new SessionUser(oauthid,oauthname);
			httpSession.setAttribute("sessionUserRegister", sessionUserRegister);
			
			ModelAndView mav = new ModelAndView();
			
			mav.setViewName("redirect:/login/terms");
			
			return mav;
			
		}
		
		//가입된 계정이면 로그인 화면으로
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/hotel/main");
		
		return mav;
		
	}
	
	
	
	
	
	//회원가입화면 띄우기
	@RequestMapping(value = "/login/register", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView register() throws Exception{
		System.out.println("회원가입창 띄우기");
		
		SessionUser sessionUserRegister = (SessionUser) httpSession.getAttribute("sessionUserRegister");
		
		//oauth 회원가입 요청일시 
		if(sessionUserRegister!=null) {
			
			System.out.println("소셜 회원가입창 진입");
			ModelAndView mav = new ModelAndView();
			
			//세션에 있는 값을 자동으로 input박스에 넣기 위함
			mav.addObject("oauthId", sessionUserRegister.getId());
	        mav.addObject("oauthName", sessionUserRegister.getName());
			mav.setViewName("login/register_oauth");
			
			return mav;
			
		}
		
		//일반 회원가입 요청일시
		System.out.println("일반 회원가입창 진입");
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("login/register");
		
		return mav;
		
	}
	
	
	
	
	//회원가입 처리
	@PostMapping("/login/register_ok")
	public ModelAndView register_ok(MemberDTO dto) throws Exception{

		System.out.println("회원가입 처리 컨트롤러 진입");
		ModelAndView mav = new ModelAndView();
		
		//소셜 회원가입 처리일시
		if(dto.getAuth()!=null) {
			
			int maxNum = memberService.maxNum();
			dto.setNum(maxNum + 1);
			dto.setAuth("소셜회원");
			
			//이메일을 dto의 email1,2에 나눠서 저장하기 위함
			String[] parts = dto.getEmail().split("@");

			dto.setEmail1(parts[0]);
			dto.setEmail2(parts[1]);
			
			memberService.insertDataSocialMember(dto);
			
			System.out.println("oauth 회원가입 성공!!");
			
			mav.setViewName("redirect:/login/login");
			
			return mav;
			
		}
		
		//일반 회원가입 처리일시
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
		
		StringBuilder sb = new StringBuilder();
		
		String tel = sb.append(tel1).append("-").append(tel2).append("-").append(tel3).toString();
		
		String result = "";
		
		SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
		
		if(sessionUser!=null) {
			
			System.out.println("회원 정보 수정시 전화번호 체크 컨트롤러 진입");
			
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("userid", sessionUser.getId());
			paramMap.put("tel",tel);
			
			//가입된 전화번호를 찾되, 기존에 등록된 자신의 전화번호는 수정되게 해야한다.
			result = memberService.checkMemberTelUpdate(paramMap);
			
			if(result!=null) {
				return "unavailable";
			}
			
			return tel;
			
		}
		
		System.out.println("회원가입시 전화번호 체크 컨트롤러 진입");
		
		result = memberService.checkMemberTel(tel);
		
		//이미 가입된 전화번호일시 ajax에 이미 가입된 번호라고 송신
		if(result!=null) {
			return "unavailable";
		}
		
		//아니면 가입 가능
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
	public ModelAndView logout(HttpServletRequest request) throws Exception{
		
		System.out.println("로그아웃 컨트롤러 진입");
		
		//로그인할때 올린 세션을 삭제
		httpSession.invalidate();
		
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("redirect:/hotel/main");
		return mav;
		
	}
	
	
	
	
	
	
	

	
	

	
}
