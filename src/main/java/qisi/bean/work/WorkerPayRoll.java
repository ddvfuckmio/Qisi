package qisi.bean.work;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author : ddv
 * @since : 2019/3/23 下午9:39
 */

@Entity(name = "worker_payrolls")
public class WorkerPayRoll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date payrollDate;
	private String username;
	private int delayCount;
	private int dayOffCount;
	private Date createdAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getPayrollDate() {
		return payrollDate;
	}

	public void setPayrollDate(Date payrollDate) {
		this.payrollDate = payrollDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getDelayCount() {
		return delayCount;
	}

	public void setDelayCount(int delayCount) {
		this.delayCount = delayCount;
	}

	public int getDayOffCount() {
		return dayOffCount;
	}

	public void setDayOffCount(int dayOffCount) {
		this.dayOffCount = dayOffCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "WorkerPayRoll{" +
				"id=" + id +
				", payrollDate=" + payrollDate +
				", username='" + username + '\'' +
				", delayCount=" + delayCount +
				", dayOffCount=" + dayOffCount +
				", createdAt=" + createdAt +
				'}';
	}
}
