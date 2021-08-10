package server_and_VO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BabyInfoDAO { // �Ʊ� ���� ���� DAOŬ����

	private Connection conn = null;
	private PreparedStatement psmt = null;
	private ResultSet rs = null;

	// jdbc ����
	public void conn() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// localhost : Oracle DB�� ��ġ�� PC�� ip�ּ� ����
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String pass = "hr";

			conn = DriverManager.getConnection(url, user, pass);

		} catch (ClassNotFoundException e) {
			System.out.println("ojdbc6.jar���� �Ǵ� driver��� Ȯ��");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB������� �Ǵ� SQL�� ����");
			e.printStackTrace();
		}

	}

	// ���� ��ü ����
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

	// �Ʊ����� ���(���ϰ� 1�̸� ����)
	public int join(String id, String babyName, String birthday) {
		conn();

		int result = 0;
		String sql = "insert into baby_info values(?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, babyName);
			ps.setString(3, birthday);

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