package qisi.bean.user;

/**
 * @author : ddv
 * @date : 2018/10/24 上午11:40
 */

import javax.persistence.*;
import java.util.Date;

@Entity(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String account;
	private String password;
	private String sex;
	private String job;
	private String age;
	private String phone;
	private String email;
	private Date createdAt;

	public User() {
	}

	public User(String account, String password, String sex, String job, String age, String phone, String email, Date createdAt) {
		this.account = account;
		this.password = password;
		this.sex = sex;
		this.job = job;
		this.age = age;
		this.phone = phone;
		this.email = email;
		this.createdAt = createdAt;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
}
