package server_and_VO;

public class BabyVO {

	private String user_id;
	private String baby_name;
	private String birthday;
	private String profile_pic;

	public BabyVO(String user_id, String baby_name, String birthday, String profile_pic) {
		super();
		this.user_id = user_id;
		this.baby_name = baby_name;
		this.birthday = birthday;
		this.profile_pic = profile_pic;
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
	
	public String getProfile_pic() {
		return profile_pic;
	}

	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}

}
