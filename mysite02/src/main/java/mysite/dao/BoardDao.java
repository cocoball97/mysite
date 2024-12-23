package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;
import mysite.vo.GuestbookVo;
import mysite.vo.UserVo;

public class BoardDao {
	
	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					// 페이징 안함
					"select a.id, title, b.name, hit, reg_date from board a join user b on a.user_id = b.id order by g_no desc, o_no asc;");	
			ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				Long hit = rs.getLong(4);
				String reg_date = rs.getString(4);
				
				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setName(name);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return result;
	}
	
	
	public void insert(BoardVo vo) {

		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("insert into board values (null, ?, ?, 1, now(), 1, 1, 1, ?)");
		) {
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setString(2, vo.getContents());
			pstmt1.setLong(3, vo.getUser_id());

			pstmt1.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	


	public BoardVo findById(Long id) {
		BoardVo vo = null;
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select id, title, contents from board where id = ?");
			
		) {
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				
				vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return vo;
	}

	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.35.205:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}


}
