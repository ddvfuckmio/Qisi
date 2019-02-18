package qisi.bean.json;

/**
 * @author : ddv
 * @date : 2019/2/18 下午2:18
 */

public class TreeJson {
	private int state;
	private String url;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TreeJson() {
	}

	public TreeJson(int state, String url) {
		this.state = state;
		this.url = url;
	}

	@Override
	public String toString() {
		return "TreeJson{" +
				"state=" + state +
				", url='" + url + '\'' +
				'}';
	}
}
