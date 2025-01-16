package mysite.controller.api;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mysite.dto.JsonResult;
import mysite.service.UserService;
import mysite.vo.UserVo;

// 빈 등록시 name
@RestController("userApiController")
@RequestMapping("/api/user")
public class UserController {
	private UserService userService;
	
	private UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/checkemail")
	public JsonResult CheckEmail(@RequestParam(value="email", required=true, defaultValue="") String email) {
		UserVo userVo = userService.getUser(email);
		
		return JsonResult.success(Map.of("exist", userVo != null));
	}
}
