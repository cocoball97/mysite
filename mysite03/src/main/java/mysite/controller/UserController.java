package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import mysite.security.Auth;
import mysite.security.AuthUser;
import mysite.service.UserService;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	private UserService userService;
	
	// 생성자 개념
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	// 매핑된 /user/join 과 리턴값 user/join 은 다르다.  맨 앞에 "/" 여부 확인!!!!
	// /user/join : 클라이언트가 요청한 url로 컨트롤러 실행
	//  user/join : 뷰 파일 경로 (설정에서 .jsp 생략)
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(UserVo userVo) {
		userService.join(userVo);	
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	// login 은 보안 문제로 controller, service 가 아닌 interceptor 부분에서 하는 것이 권장됨
	// 연습을 위해 여기서 진행
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	// 보안 관련된 로그인 로그아웃은 인터셉터에서 처리되는 것이 권장
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	// 인자는 객체를 받는 것 추천
//	// Model : 컨트롤러 -> 뷰. 렌더링용 데이터 전달 /  vo객체 : 데이터 처리 . (내가 보기엔 데이터 전달받기 위한?)
//	public String login(HttpSession session, UserVo userVo, Model model) {
//		UserVo authUser = userService.getUser(userVo.getEmail(), userVo.getPassword());
//		if(authUser == null) {
//			model.addAttribute("email", userVo.getEmail());
//			model.addAttribute("result", "fail");
//			return "user/login";
//		}
//		
//		// login 처리
//		session.setAttribute("authUser",authUser);
//		return "redirect:/";
//	}
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		// 세션종료
//		session.invalidate();
//		
//		return "redirect:/";
//	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
		UserVo userVo = userService.getUser(authUser.getId());
		
		model.addAttribute("vo", userVo);
		return "user/update";
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.POST)
											// 이게 데이터 바인딩
	public String update(@AuthUser UserVo authUser, UserVo userVo) {		
		userVo.setId(authUser.getId());	
		userService.update(userVo);
		
		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}	
}
