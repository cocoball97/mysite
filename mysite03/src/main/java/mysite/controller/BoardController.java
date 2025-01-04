package mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mysite.service.BoardService;



@Controller
@RequestMapping("/board")
public class BoardController {
	private  BoardService boardService;
	
	public String list() {
		
		// 여러가지 데이터를 한번에 반환하기 위해 Map 사용
		Map<String, Object> map = boardService.getContentsList(page);
		
		return "board/list";
	}
	
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
//	public String delete() {
//		
//	}
//	
//	public String modify() {
//		
//	}
//	
//	public String view() {
//		
//	}
//	
//	public Stirng write() {
//		
//	}

}
