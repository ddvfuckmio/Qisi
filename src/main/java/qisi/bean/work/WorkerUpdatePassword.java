package qisi.bean.work;

/**
 * @author : ddv
 * @date : 2019/2/19 下午3:15
 */

public class WorkerUpdatePassword {
	private String username;
	private String password;
	private String newPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "WorkerUpdatePassword{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", newPassword='" + newPassword + '\'' +
				'}';
	}
}
