package qisi.bean.user;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author : ddv
 * @date : 2018/11/12 上午9:41
 */

public class MockUser {

	private String username;
	private String sex;
	private String job;
	private String age;
	private String phone;
	private String email;
	private String role;
	private Date createdAt;

	public MockUser() {
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "MockUser{" +
				"username='" + username + '\'' +
				", sex='" + sex + '\'' +
				", job='" + job + '\'' +
				", age='" + age + '\'' +
				", phone='" + phone + '\'' +
				", email='" + email + '\'' +
				", role='" + role + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
}
