package mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.security.Auth;
import mysite.security.AuthUser;
import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;
import mysite.web.util.WebUtil;


@Controller
@RequestMapping("/board")
public class BoardController {
	private  BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@RequestMapping("")
	public String index(
			@RequestParam(value="p", required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="") String keyword,
			Model model) {
			
			Map<String, Object> map = boardService.getContentsList(page, keyword);
			model.addAttribute("map", map);
			model.addAttribute("keyword", keyword);
			
			return "board/index";
		}
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable("id") Long id, Model model) {
		BoardVo boardVo = boardService.getContents(id);
		System.out.println("2222222222222222"+boardVo.getId());
		model.addAttribute("boardVo", boardVo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping("/delete/{id}")
	public String delete(
			@AuthUser UserVo authUser,
			@PathVariable("id") Long boardId,
			@RequestParam(value="p", required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
		
			boardService.deleteContents(boardId, authUser.getId());
			
			return "redirect:/board?p=" + page + "&kwd=" + WebUtil.encodeURL(keyword, "UTF-8");
		}

	@Auth
	@RequestMapping("/modify/{id}")	
	public String modify(
			@AuthUser UserVo authUser, 
			@PathVariable("id") Long id, 
			Model model) {
		
		BoardVo boardVo = boardService.getContents(id, authUser.getId());
		model.addAttribute("boardVo", boardVo);
		
		return "board/modify";
	}

	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.POST)	
	public String modify(
		@AuthUser UserVo authUser, 
		BoardVo boardVo,
		@RequestParam(value="p", required=true, defaultValue="1") Integer page,
		@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {		
		boardVo.setUserId(authUser.getId());
		boardService.modifyContents(boardVo);
		System.out.println("아닌가           여긴가???????"+boardVo.getId());
		return "redirect:/board/view/" + boardVo.getId() + 
				"?p=" + page + 
				"&kwd=" + WebUtil.encodeURL( keyword, "UTF-8" );
	}

	@Auth
	@RequestMapping(value = "/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value = "/write", method=RequestMethod.POST)
	public String write(
			@AuthUser UserVo authUser,
			// @ModelAttribute 매개변수는 클라이언트에서 전송된 폼 데이터를 기반으로 자동으로 vo 객체에 매핑 (=바인딩)
			@ModelAttribute BoardVo boardVo,
			@RequestParam(value="p", required=true, defaultValue="1") Integer page,
			@RequestParam(value="kwd", required=true, defaultValue="") String keyword) {

			boardVo.setUserId(authUser.getId());
			boardService.addContents(boardVo);
			
			// WebUtil.encodeURL : URL 허용되지 않는 문자 처리를 위해
			return	"redirect:/board?p=" + page + "&kwd=" + WebUtil.encodeURL(keyword, "UTF-8");
		}

	@Auth
	@RequestMapping(value="/reply/{id}")	
	public String reply(@PathVariable("id") Long id, Model model) {
		BoardVo boardVo = boardService.getContents(id);
		boardVo.setOrderNo(boardVo.getOrderNo() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);
		
		model.addAttribute("boardVo", boardVo);
		
		return "board/reply";
	}	
}
