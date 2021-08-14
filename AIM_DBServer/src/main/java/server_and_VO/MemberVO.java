package server_and_VO;

public class MemberVO {

	private String user_id;
	private String user_pw;
	private String user_email;
	private String user_ip;

	public MemberVO(String user_id, String user_pw, String user_email, String user_ip) {
		super();
		this.user_id = user_id;
		this.user_pw = user_pw;
		this.user_email = user_email;
		this.user_ip = user_ip;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_pw() {
		return user_pw;
	}

	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

}