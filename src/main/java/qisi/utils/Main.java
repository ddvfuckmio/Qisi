package qisi.utils;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : ddv
 * @date : 2018/11/12 下午2:02
 */

public class Main {


	public static void main(String[] args) {
		System.out.println(1);
		File file = new File("images/基础7.mp4");
		try {
			InputStream in = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream("images/copy.mp4");
			byte[] bytes = new byte[1024];
			int len ;
			while ((len = (in.read(bytes))) != -1) {
				fos.write(bytes, 0, len);
			}
			fos.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
