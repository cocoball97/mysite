package mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public void insert(BoardVo boardVo) {
		sqlSession.insert("board.insert", boardVo);
	}
	
	// java 8 이하에서는 HashMap을 사용해야 한다. ( Map<String, Long> map = new HashMap<String, Long>(); )
	// java 9 이후, Map.of()를 사용해 쉽게 Map 생성
	public BoardVo findByIdAndUserID(Long id, Long userId) {
		return sqlSession.selectOne("board.findByIdAndUserId", Map.of("id", id, "userId", userId));
	}
	
	public void delete(Long boardNo, Long userId) {
		sqlSession.delete("board.delete", Map.of("boardNo", boardNo, "userId", userId));
	}	

	public List<BoardVo> findAllByPageAndKeword(String keyword, int page, int size) {
		return sqlSession.selectList("board.findAllByPageAndKeword", Map.of("keyword", keyword, "startIndex", (page-1)*size, "size", size));
	}
	
    public int update(BoardVo boardVo) {
        return sqlSession.update("board.update", boardVo);
    }

    public BoardVo findById(Long id) {
        return sqlSession.selectOne("board.findById", id);
    }

    public BoardVo findByIdAndUserId(Long id, Long userid) {        
        return sqlSession.selectOne("board.findByIdAndUserId", Map.of("id", id, "userId", userid));
    }

    public int updateHit(Long id) {
        return sqlSession.update("board.updateHit", id);
    }
    
	public void updateOrderNo(Integer groupNo, Integer orderNo) {
		sqlSession.update("board.updateOrederNo", Map.of("groupNo", groupNo, "orderNo", orderNo));
	}

	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.totalCount", keyword);
	}

}
