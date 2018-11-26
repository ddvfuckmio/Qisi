package qisi.utils;

import qisi.bean.user.User;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : ddv
 * @date : 2018/11/12 下午2:02
 */

public class Main {
	private static HashMap<Integer, Integer> hashMap = new HashMap<>();

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			System.out.println(Utils.getUUID());
		}
	}

	private static void fileWriter() {
		File file = new File("images/dva.mp4");
		try {
			FileOutputStream fos = new FileOutputStream("images/b.mp4");
			FileInputStream in = new FileInputStream(file);
			FileChannel channel = fos.getChannel();

			ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 1024 * 5);
			byte[] bytes = new byte[1024];
			while (in.read(bytes) != -1) {
				buffer.put(bytes);

			}
			buffer.flip();
			channel.write(buffer);
			channel.close();
			fos.close();
			long start = System.currentTimeMillis();

			long end = System.currentTimeMillis();
			System.out.println((end - start) / 1000 + "." + (end - start) % 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
