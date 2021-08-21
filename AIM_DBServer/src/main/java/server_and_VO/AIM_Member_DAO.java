package server_and_VO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AIM_Member_DAO { // 회원 DB관리 DAO클래스

	private Connection conn = null;
	private PreparedStatement psmt = null;
	private ResultSet rs = null;

	// jdbc 연결
	public void conn() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// localhost : Oracle DB가 설치된 PC의 ip주소 설정
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String pass = "hr";

			conn = DriverManager.getConnection(url, user, pass);

		} catch (ClassNotFoundException e) {
			System.out.println("ojdbc6.jar파일 또는 driver경로 확인");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB연결실패 또는 SQL문 오류");
			e.printStackTrace();
		}

	}

	// 연결 객체 종료
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}

			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 회원가입(리턴값 1이면 성공)
	public int join(String id, String pw, String email, String ip) {
		conn();

		int result = 0;
		String sql = "insert into aim_members values(?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			ps.setString(3, email);
			ps.setString(4, ip);
			ps.setString(5, "defult");

			result = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}
	
	// 로그인(리턴값 true면 성공, false면 실패)
	public boolean login(String id, String pw) {
		conn();
		
		boolean success = false;
		
		String sql = "select member_id from aim_members where member_id = ? and password = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				success = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return success;
	}

	public ArrayList<MemberVO> select_member(String id) {
		conn();
		
		ArrayList<MemberVO> list = new ArrayList<>();
		MemberVO vo = null;
		
		String sql = "select * from aim_members where member_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String user_id = rs.getString(1);
				String user_pw = rs.getString(2);
				String user_email = rs.getString(3);
				String user_ip = rs.getString(4);
				String profile_pic = rs.getString(5);

			    vo = new MemberVO(user_id,user_pw,user_email,user_ip,profile_pic);
			    list.add(vo);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close();
		
		return list;
	}
	
	public int update_member(String id,String pw, String email, String ip) {
		
		conn();
		
		int cnt = 0;
		
		String sql = "update aim_members set password = ?,email = ?,ip = ? where member_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, pw);
			ps.setString(2, email);
			ps.setString(3, ip);
			ps.setString(4, id);
			
			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		
		return cnt;
	}
	
}