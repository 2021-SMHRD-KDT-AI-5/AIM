package server_and_VO;

public class BabyVO {

	private String user_id;
	private String baby_name;
	private String birthday;

	public BabyVO(String user_id, String baby_name, String birthday) {
		super();
		this.user_id = user_id;
		this.baby_name = baby_name;
		this.birthday = birthday;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getBaby_name() {
		return baby_name;
	}

	public void setBaby_name(String baby_name) {
		this.baby_name = baby_name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}
