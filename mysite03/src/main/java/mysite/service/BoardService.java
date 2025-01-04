package mysite.service;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
//	public BoardVo getContents(Long id) {
//		return boardRepository.findById(id);
//	}
//	
//	public void addContents(BoardVo boardVo) {
//		boardRepository.insert(boardVo);
//	}
//	

//	
//	public BoardVo getContents(Long id, Long userId) {
//		
//	}
//	
//	public void updateContents(BoardVo vo) {
//		
//	}
//	
//	public void deleteContents(Long id, String userId) {
//		
//	}
	

}
