package mysite.repository;

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

}
