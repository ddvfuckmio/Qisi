package qisi.utils;

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
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		String phone = "1515512185";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = (pattern.matcher(phone));
		System.out.println(matcher.matches());
	}

	private static void normal() {
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
