package server_and_VO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		String sql = "insert into baby_info values(?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, babyName);
			ps.setString(3, birthday);
			ps.setString(4, "defult");

			result = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}

	public ArrayList<BabyVO> select_baby(String id) {

		conn();

		ArrayList<BabyVO> list = new ArrayList<>();
		BabyVO vo = null;

		String sql = "select * from BABY_INFO where parent_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();

			while (rs.next()) {
				String p_name = rs.getString(1);
				String baby_name = rs.getString(2);
				String baby_birtday = rs.getString(3);
				String profile_pic = rs.getString(4);

				vo = new BabyVO(p_name, baby_name, baby_birtday, profile_pic);
				list.add(vo);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		close();

		return list;
	}

	public int update_baby(String id, String baby_name, String baby_bday) {

		conn();

		int cnt = 0;

		String sql = "update BABY_INFO set baby_name = ?,birthday = ? where parent_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, baby_name);
			ps.setString(2, baby_bday);
			ps.setString(3, id);

			cnt = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}

		System.out.println("cnt�� � ? : " + cnt);
		System.out.println("id = " + id);
		System.out.println("name = " + baby_name);
		System.out.println("day = " + baby_bday);

		return cnt;
	}

	public int update_profile_pic(String id, String profilePic) {// �Ʊ� �����ʻ��� ����

		conn();

		int cnt = 0;

		String sql = "update baby_info set profile_pic = ? where parent_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, profilePic);
			ps.setString(2, id);

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
