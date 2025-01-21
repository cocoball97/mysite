package mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {
	private static final int LIST_SIZE = 5; //리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; //페이지 리스트의 페이지 수
	
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	public void addContents(BoardVo boardVo) {
		// 그룹번호가 있으므로 답글
		if(boardVo.getGroupNo() != null) {
			boardRepository.updateOrderNo(boardVo.getGroupNo(), boardVo.getOrderNo());
		}
		
		// 그룹번호가 없으므로 답글이 아닌 새 글 작성
		boardRepository.insert(boardVo);
	}

	public BoardVo getContents(Long id) {
		BoardVo boardVo = boardRepository.findById(id);
		// 기존에 글이 있으면 조회수 상승
		if(boardVo != null) {
			boardRepository.updateHit(id);
		}	
		return boardVo;
	}
	
	public BoardVo getContents(Long id, Long userId) {
		return boardRepository.findByIdAndUserID(id, userId);		 
	}
	
	public void modifyContents(BoardVo boardVo) {
		boardRepository.update(boardVo);
	}
	
	public void deleteContents(Long boardNo, Long userId) {
		boardRepository.delete(boardNo, userId);
	}
	
public Map<String, Object> getContentsList(int currentPage, String keyword) {
		
		// DB 관련이 아닌 서비스는 여기서 처리한다!
		//1. 페이징을 위한 기본 데이터 계산
		int totalCount = boardRepository.getTotalCount(keyword); 
		int pageCount = (int)Math.ceil((double)totalCount / LIST_SIZE);
		int blockCount = (int)Math.ceil((double)pageCount / PAGE_SIZE);
		int currentBlock = (int)Math.ceil((double)currentPage / PAGE_SIZE);
		
		//2. 파라미터 page 값  검증
		if(currentPage > pageCount) {
			currentPage = pageCount;
			currentBlock = (int)Math.ceil((double)currentPage / PAGE_SIZE);
		}		
		
		if(currentPage < 1) {
			currentPage = 1;
			currentBlock = 1;
		}
		
		//3. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
		int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1) * PAGE_SIZE + 1;
		int prevPage = (currentBlock > 1 ) ? (currentBlock - 1) * PAGE_SIZE : 0;
		int nextPage = (currentBlock < blockCount) ? currentBlock * PAGE_SIZE + 1 : 0;
		int endPage = (nextPage > 0) ? (beginPage - 1) + LIST_SIZE : pageCount;
		
		//4. 리스트 가져오기
		List<BoardVo> list = boardRepository.findAllByPageAndKeword(keyword, currentPage, LIST_SIZE);
		
		//5. 리스트 정보를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("listSize", LIST_SIZE);
		map.put("currentPage", currentPage);
		map.put("beginPage", beginPage);
		map.put("endPage", endPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("keyword", keyword);

		return map;
	}

}
