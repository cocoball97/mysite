package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mysite.security.Auth;
import mysite.service.AdminService;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@RequestMapping({"","/main"})
	public String main() {
		return "admin/main";
	}
	
	@RequestMapping("/main/update")
	public String update(
			// multipart 설정 추가하고 vo로 받을 수 있음??? 아닌
			@RequestParam("title") String title,
			@RequestParam("welcomeMessage") String welcomeMessage,
			@RequestParam("description") String description,
			@RequestParam("file") MultipartFile multipartfile) { // 이름 유의 jsp보
		
//		String url = fileUploadService.restore(file);
		return "admin/main";
	}
	
	
	
	
	
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}	
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
