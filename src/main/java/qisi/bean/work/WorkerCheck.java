package qisi.bean.work;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author : ddv
 * @date : 2019/1/27 下午2:38
 */
@Entity(name = "worker_checks")
public class WorkerCheck {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private Date signIn;
	private Date signOut;
	private Date checkDay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getSignIn() {
		return signIn;
	}

	public void setSignIn(Date signIn) {
		this.signIn = signIn;
	}

	public Date getSignOut() {
		return signOut;
	}

	public void setSignOut(Date signOut) {
		this.signOut = signOut;
	}

	public Date getCheckDay() {
		return checkDay;
	}

	public void setCheckDay(Date checkDay) {
		this.checkDay = checkDay;
	}

	@Override
	public String toString() {
		return "WorkerCheck{" +
				"id=" + id +
				", username='" + username + '\'' +
				", signIn=" + signIn +
				", signOut=" + signOut +
				", checkDay=" + checkDay +
				'}';
	}
}
