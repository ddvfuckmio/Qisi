package qisi.utils;

import java.util.concurrent.Callable;

/**
 * @author : ddv
 * @date : 2018/11/27 下午3:21
 */

public class ListenConsumer implements Callable<Boolean> {
	private String receiveName;
	private String codeId;

	public ListenConsumer(String receiveName, String codeId) {
		this.receiveName = receiveName;
		this.codeId = codeId;
	}

	@Override
	public Boolean call() {
		return Jms.consumer(receiveName, codeId);
	}
}
