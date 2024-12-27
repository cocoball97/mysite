package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import mysite.vo.UserVo;

@Repository
public class UserRepository {

	public int insert(UserVo vo) {
		int count = 0;

		try (
				Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("insert into user values (null, ?, ?, ?, ?, now())");
		) {
			pstmt1.setString(1, vo.getName());
			pstmt1.setString(2, vo.getEmail());
			pstmt1.setString(3, vo.getPassword());
			pstmt1.setString(4, vo.getGender());

			count = pstmt1.executeUpdate();

		} catch (SQLException e) {
			System.out.println("book error1:" + e);
		}

		return count;
	}
	

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo userVo = null;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select id, name from user where email=? and password=?");
		) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				
				userVo = new UserVo();
				userVo.setId(id);
				userVo.setName(name);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return userVo;
	}
	
	public UserVo findById(Long user_id) {
		UserVo vo = null;
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select id,name,email,gender from user where id = ?");
			
		) {
			pstmt.setLong(1, user_id);

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				
				vo = new UserVo();
				vo.setId(id);
				vo.setName(name);
				vo.setEmail(email);
				vo.setGender(gender);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("book error2:" + e);
		}
		return vo;
	}

	public UserVo update(UserVo vo) {

		try (
			Connection conn = getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement("update user set name=?, gender=? where id = ?");
			PreparedStatement pstmt2 = conn.prepareStatement("update user set name=?, gender=?, password=? where id = ?");
			
		) {
			if("".equals(vo.getPassword())) {
				pstmt1.setString(1, vo.getName());
				pstmt1.setString(2, vo.getGender());
				pstmt1.setLong(3, vo.getId());
				pstmt1.executeUpdate();
			} else {
				pstmt2.setString(1, vo.getName());
				pstmt2.setString(2, vo.getGender());
				pstmt2.setString(3, vo.getPassword());
				pstmt2.setLong(4, vo.getId());
				pstmt2.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("book error3:" + e);
		}
		return vo;
	}
	
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.35.9:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}
}
