package qisi.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : ddv
 * @date : 2018/11/12 下午2:02
 */

public class Main {
	private static ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, String> hashMap2 = new ConcurrentHashMap<>();

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			new Thread(
					() -> {
						while (true) {
							String uuid = Utils.getUUID();
							hashMap.put(uuid, uuid);
							hashMap2.put(uuid, uuid);
							System.out.println(
									uuid + " " + Thread.currentThread().getName());
						}
					})
					.start();
		}

		for (; ; ) {
		}
	}
}
