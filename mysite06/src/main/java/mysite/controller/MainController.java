package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mysite.vo.UserVo;

@Controller
public class MainController {
	@RequestMapping({"/", "/main"})
	public String main(Model model) {
		return "main/index";
	}
	
	// message converter
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "Hello World";
	}
	
	// message converter
	@RequestMapping("/msg02")
	public String message02() {
		return "dgfdgfdg";
	}

	
	// jackson
	@ResponseBody
	@RequestMapping("/msg03")
	public Object message03() {
		UserVo vo = new UserVo();
		vo.setId(10L);
		vo.setName("둘리");
		vo.setEmail("dooly@gmail.com");
		return vo;
	}
}