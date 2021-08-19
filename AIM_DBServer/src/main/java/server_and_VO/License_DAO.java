package server_and_VO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class License_DAO { // ȸ�� DB���� DAOŬ����

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

	// ���ױ� ���
	public int AddLicense(String id, String type, String codeOrNot) {
		conn();

		int result = 0;
		String sql = "insert into license values(?,?,?,sysdate)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, type);
			ps.setString(3, codeOrNot);
			

			result = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}

	public boolean LicenseChecked(String id) {
		conn();

		boolean success = false;

		String sql = "select member_id from license where member_id = ?";
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

	public String select_License(String id) {
		conn();

		String str = "";

		String sql = "select * from license where member_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {
				String user_id = rs.getString(1);
				String license_type = rs.getString(2);
				String license_codeOrnot = rs.getString(3);
				String use_date = rs.getString(4);

				str =  license_type + "," + license_codeOrnot + "," + use_date;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		close();
		System.out.println(str);
		
		return str;
	}

	public int Delete_License(String id) {
		conn();

		int result = 0;
		String sql = "delete from license where member_id = ?";
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
