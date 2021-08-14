package server_and_VO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Bohum_DAO { // 회원 DB관리 DAO클래스

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

	// 보험등록
	public int AddBo(String id, String name, String bs_name, String b_name) {
		conn();

		int result = 0;
		String sql = "insert into insurance values(?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, bs_name);
			ps.setString(4, b_name);

			result = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}

	public boolean BoChecked(String id) {
		conn();

		boolean success = false;

		String sql = "select member_id from insurance where member_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				success = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return success;
	}

	public String select_Bo(String id) {
		conn();

		String str = "";

		String sql = "select * from insurance where member_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {
				String user_id = rs.getString(1);
				String user_name = rs.getString(2);
				String bs = rs.getString(3);
				String b = rs.getString(4);

				str = user_name + "," + bs + "," + b;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		close();
		System.out.println(str);
		
		return str;
	}

	public int DeleteBo(String id) {
		conn();

		int result = 0;
		String sql = "delete from insurance where member_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			result = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}
	
}
