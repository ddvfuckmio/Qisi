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
	private int earlyCount;
	private int dayOffCount;
	private int absentCount;
	private String department;
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

	public int getEarlyCount() {
		return earlyCount;
	}

	public void setEarlyCount(int earlyCount) {
		this.earlyCount = earlyCount;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getAbsentCount() {
		return absentCount;
	}

	public void setAbsentCount(int absentCount) {
		this.absentCount = absentCount;
	}

	@Override
	public String toString() {
		return "WorkerPayRoll{" +
				"id=" + id +
				", payrollDate=" + payrollDate +
				", username='" + username + '\'' +
				", delayCount=" + delayCount +
				", earlyCount=" + earlyCount +
				", dayOffCount=" + dayOffCount +
				", absentCount=" + absentCount +
				", department='" + department + '\'' +
				", createdAt=" + createdAt +
				'}';
	}
}
