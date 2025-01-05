package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mysite.security.Auth;
import mysite.service.FileUploadService;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	private final SiteService siteService;
	// 비즈니스 로직이 아닌 파일 업로드 로직 개별적으로 필요
	private final FileUploadService fileUploadService;
	
	public AdminController(SiteService siteService, FileUploadService fileUploadService) {
		this.siteService = siteService;
		this.fileUploadService = fileUploadService;
	}
	
	@RequestMapping({"", "/main"})
	public String main(Model model) {
		SiteVo siteVo = siteService.getSite();
	    model.addAttribute("siteVo", siteVo);

	    // siteVo에서 profile 값을 직접 출력
	    System.out.println("=================== Profile: " + siteVo.getProfile());
	    return "admin/main";
	    
	    
//		model.addAttribute("siteVo", siteService.getSite());
//		return "admin/main";
	}
	
	@RequestMapping("/main/update")
	public String update(SiteVo siteVo, @RequestParam("file") MultipartFile multipartFile) {
		String profile = fileUploadService.restore(multipartFile);
		if(profile != null) {
			siteVo.setProfile(profile);
		}
		
		siteService.updateSite(siteVo);
		return "redirect:/admin";
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
