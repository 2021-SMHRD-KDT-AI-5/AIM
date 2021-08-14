package server_and_VO;

public class Bohum_VO {

	private String user_id;
	private String user_name;
	private String bs_name;
	private String b_name;

	public Bohum_VO(String user_id, String user_name, String bs_name, String b_name) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.bs_name = bs_name;
		this.b_name = b_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getBs_name() {
		return bs_name;
	}

	public void setBs_name(String bs_name) {
		this.bs_name = bs_name;
	}

	public String getB_name() {
		return b_name;
	}

	public void setB_name(String b_name) {
		this.b_name = b_name;
	}

}
