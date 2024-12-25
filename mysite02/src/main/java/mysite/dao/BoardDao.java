package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;

public class BoardDao {
	
	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					// 페이징 안함
//					"select a.id, title, b.name, hit, reg_date from board a join user b on a.user_id = b.id order by g_no desc, o_no asc;");	
					"select a.id, a.title, a.contents, a.hit, a.reg_date, a.g_no, a.o_no, a.dept, a.user_id, b.name from board a join user b on a.user_id = b.id order by g_no desc, o_no asc;");	
			ResultSet rs = pstmt.executeQuery();
		) {
			System.out.println("findall에러");
			
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String reg_date = rs.getString(5);
				Long g_no = rs.getLong(6);
				Long o_no = rs.getLong(7);
				Long dept = rs.getLong(8);
				Long user_id = rs.getLong(9);
				String name = rs.getString(10);
				
				
				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDept(dept);
				vo.setUser_id(user_id);
				vo.setName(name);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return result;
	}
	
	
	public void insert(BoardVo vo) {

		// g_no 최대값 가져오기
		Long g_no_max = 0L;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT IFNULL(MAX(g_no), 0) FROM board");
			    ResultSet rs = pstmt.executeQuery()) {
			    if (rs.next()) {
			    	g_no_max = rs.getLong(1);
			    	vo.setG_no(g_no_max);
		    }
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("insert into board values (null, ?, ?, ?, now(), IFNULL(?, 0) + 1, ?, ?,?)");
//				PreparedStatement pstmt1 = conn.prepareStatement("insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?)";);
		) {
			System.out.println("insert에러");
			
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setString(2, vo.getContents());
			pstmt1.setLong(3, vo.getHit());
			pstmt1.setLong(4, vo.getG_no());
			pstmt1.setLong(5, vo.getO_no());
			pstmt1.setLong(6, vo.getDept());
			pstmt1.setLong(7, vo.getUser_id());

			pstmt1.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
//	public void insert_reply(BoardVo vo) {
//		try (
//			Connection conn = getConnection();
//			PreparedStatement pstmt = conn.prepareStatement("select title, contents, user_id from board where id = ?");
//			
//		) {
//			pstmt.setLong(1, id);
//
//			ResultSet rs = pstmt.executeQuery();
//			while(rs.next()) {
//				String title = rs.getString(1);
//				String contents = rs.getString(2);
//				Long user_id = rs.getLong(3);
//				
//				vo = new BoardVo();
//				vo.setId(id);
//				vo.setTitle(title);
//				vo.setContents(contents);
//				vo.setUser_id(user_id);
//			}
//			rs.close();
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		}
//	}
		


	public BoardVo findById(Long id) {
		BoardVo vo = null;
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select title, contents, hit, g_no, o_no, dept, user_id from board where id = ?");
//				PreparedStatement pstmt = conn.prepareStatement("select title, contents, user_id from board where id = ?");
			
			
		) {
			pstmt.setLong(1, id);
			
			System.out.println("findbyid에러");
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				Long hit = rs.getLong(3);
				Long g_no = rs.getLong(4);
				Long o_no = rs.getLong(5);
				Long dept = rs.getLong(6);
				Long user_id = rs.getLong(3);
				
				vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDept(dept);
				vo.setUser_id(user_id);
				
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return vo;
	}
	
	public BoardVo modify(BoardVo vo) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("update board set title = ?, contents = ? where id = ?");
		) {
			pstmt1.setString(1, vo.getTitle());
			pstmt1.setString(2, vo.getContents());
			pstmt1.setLong(3, vo.getId());

			pstmt1.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return vo;
	}


	public void deleteById(Long id) {
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from board where id=?");
		) {
			pstmt.setLong(1, id);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 	
	}

	
	public Long findMaxGNo() {
		Long maxGNo = 0L;
	    try (
	        Connection conn = getConnection();
	        PreparedStatement pstmt = conn.prepareStatement("SELECT IFNULL(MAX(g_no), 0)+1 FROM board");
	        ResultSet rs = pstmt.executeQuery();
	    ) {
	        if (rs.next()) {
	            maxGNo = rs.getLong(1);
	        }
	    } catch (SQLException e) {
	        System.out.println("error: " + e);
	    }
	    return maxGNo;
	}
	
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.35.100:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}




}
