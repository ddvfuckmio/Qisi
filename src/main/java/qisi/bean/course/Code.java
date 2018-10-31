package qisi.bean.course;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : ddv
 * @date : 2018/10/30 上午11:40
 */

@Entity(name = "user_codes")
public class Code {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String codeId;
	private String username;
	private String exerciseId;
	@Lob
	@Column(columnDefinition = "text")
	private String code;
	private boolean pass;
	private Date createdAt;

	public Code() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(String exerciseId) {
		this.exerciseId = exerciseId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
