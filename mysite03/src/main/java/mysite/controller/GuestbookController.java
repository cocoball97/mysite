package mysite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.repository.GuestbookRepository;
import mysite.service.GuestbookService;
import mysite.vo.GuestbookVo;

// @requestmapping "/" 아니라 "" 이런거 있음

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	private GuestbookService guestbookService;
	
	public GuestbookController(GuestbookService guestbookService) {
		this.guestbookService = guestbookService;
	}
	
	@RequestMapping(value="")
	public String index(Model model) {
		List<GuestbookVo> list = guestbookService.getContentsList();
		System.out.println("list:"+list);
//		model.addAttribute("list",list);
//		List<GuestbookVo> list = guestbookService.getContentsList();
//		System.out.println("Retrieved guestbook entries: " + list);
		
		model.addAttribute("list", guestbookService.getContentsList());
		return "guestbook/index";
	}
	
	@RequestMapping(value="/add")
	public String add(GuestbookVo guestbookVo) {
		guestbookService.addContents(guestbookVo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		return "/guestbook/delete";
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String delete(@PathVariable("id") Long id, @RequestParam ("password") String password) {
		guestbookService.deleteContents(id, password);
		return "redirect:/guestbook";
	}
}
