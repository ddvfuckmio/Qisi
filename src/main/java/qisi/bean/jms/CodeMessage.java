package qisi.bean.jms;

import java.io.Serializable;
import java.util.List;

/**
 * @author : ddv
 * @date : 2018/11/7 上午11:53
 */

public class CodeMessage implements Serializable {
	private String codeId;
	private int maxTime;
	private int maxMemory;
	private int totalCases;
	private String code;
	private String firstCode;
	private String secondCode;
	private String type;
	private List<String> inputs;
	private List<String> outputs;

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public int getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(int maxMemory) {
		this.maxMemory = maxMemory;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFirstCode() {
		return firstCode;
	}

	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}

	public String getSecondCode() {
		return secondCode;
	}

	public void setSecondCode(String secondCode) {
		this.secondCode = secondCode;
	}

	public List<String> getInputs() {
		return inputs;
	}

	public void setInputs(List<String> inputs) {
		this.inputs = inputs;
	}

	public List<String> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<String> outputs) {
		this.outputs = outputs;
	}

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CodeMessage{" +
				"codeId='" + codeId + '\'' +
				", maxTime=" + maxTime +
				", maxMemory=" + maxMemory +
				", totalCases=" + totalCases +
				", code='" + code + '\'' +
				", firstCode='" + firstCode + '\'' +
				", secondCode='" + secondCode + '\'' +
				", type='" + type + '\'' +
				", inputs=" + inputs +
				", outputs=" + outputs +
				'}';
	}
}
