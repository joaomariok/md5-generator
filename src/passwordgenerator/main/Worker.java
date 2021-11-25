package passwordgenerator.main;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Worker {
	
	private final String EMPTY_STRING = "";
	private final int HASH_CHAR_COUNT = 32;

	public Worker() {
		System.out.println("[Worker] Creating");
	}
	
	public String Execute(String text) throws Exception {
		System.out.println("[Worker] Executing");
		
		if (text.isEmpty()) {
			System.out.println("[Worker] Empty string");
			return EMPTY_STRING;
		}
		
		String hash = getMD5Hash(text);
		
		System.out.println("[Worker] Text: " + text + " | MD5: " + hash);
		
		return hash;
	}
	
	private String getMD5Hash(String text) throws Exception {
		String s = text;
		
		MessageDigest m = MessageDigest.getInstance("MD5");
		
		m.update(s.getBytes(), 0, s.length());
		
		String md5 = new BigInteger(1, m.digest()).toString(16);
		
		while (md5.length() < HASH_CHAR_COUNT) {
			md5 = "0" + md5;
		}
		
		return md5;
	}
}
