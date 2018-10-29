package qisi.utils;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * @author : ddv
 * @date : 2018/10/29 下午1:14
 */

public class Utils {
	private static final String SALT = "qisi";

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}


	public static String encode(String password) {
		password = password + SALT;
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		char[] charArray = password.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}

			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	public static void main(String[] args) {
		for(int i = 0;i<10;i++){
			System.out.println(getUUID());
		}
	}
}
