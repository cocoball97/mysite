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

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(
				"select a.id, a.title, a.contents, a.hit, a.reg_date, a.g_no, a.o_no, a.dept, a.user_id, b.name from board a join user b on a.user_id = b.id order by g_no desc, o_no asc;");
				ResultSet rs = pstmt.executeQuery();) {

			while (rs.next()) {
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

	public Long findMaxGNo() {
		Long maxGNo = 0L;
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(g_no) FROM board");
				ResultSet rs = pstmt.executeQuery();) {
			if (rs.next()) {
				maxGNo = rs.getLong(1);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		// 최대치 +1
		return maxGNo + 1;
	}
	
	
	public void insert(BoardVo vo) {

		try (Connection conn = getConnection();
				PreparedStatement pstmt1 = conn
						.prepareStatement("insert into board values (null, ?, ?, 0, now(), ?, ?, ?,?)");) {

			pstmt1.setString(1, vo.getTitle());
			pstmt1.setString(2, vo.getContents());
			pstmt1.setLong(3, vo.getG_no());
			pstmt1.setLong(4, vo.getO_no());
			pstmt1.setLong(5, vo.getDept());
			pstmt1.setLong(6, vo.getUser_id());

			pstmt1.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public BoardVo findById(Long id) {
		BoardVo vo = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select title, contents, hit, g_no, o_no, dept, user_id from board where id = ?");
//				PreparedStatement pstmt = conn.prepareStatement("select title, contents, user_id from board where id = ?");

		) {
			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				Long hit = rs.getLong(3);
				Long g_no = rs.getLong(4);
				Long o_no = rs.getLong(5);
				Long dept = rs.getLong(6);
				Long user_id = rs.getLong(7);

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
	
	public void updateNo(Long g_no, Long o_no, Long dept) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("update board set o_no=o_no+1 where g_no=? and o_no>=?+1");
			) {
				pstmt.setLong(1, g_no);
				pstmt.setLong(2, o_no);
				
				pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public List<BoardVo> findBypage(int write_no, int write_page) {
List<BoardVo> result = new ArrayList<BoardVo>();
		
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select b.id, b.title, u.name, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.dept, u.id" +
						"  from board b join user u" +
						"    on b.user_id=u.id" +
						" order by g_no desc, o_no asc" + 
						" limit ?, ?");
		)
		{
			pstmt.setInt(1, write_no);
			pstmt.setInt(2, write_page);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				Long hit = rs.getLong(4);
				String reg_date = rs.getString(5);
				Long dept = rs.getLong(6);
				Long userId = rs.getLong(7);
				
				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setName(name);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setDept(dept);
				vo.setUser_id(userId);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		return result;
	}
	
	public BoardVo modify(BoardVo vo) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt1 = conn
						.prepareStatement("update board set title = ?, contents = ? where id = ?");
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


	public void updateView(Long id) {
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("update board set hit=hit+1 where id=?");
		) {
			
				pstmt.setLong(1, id); 
				
				pstmt.executeUpdate();
				
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}
	
	
	public void deleteById(Long id) {

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from board where id=?");) {
			pstmt.setLong(1, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}


	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.71.1:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}
}
