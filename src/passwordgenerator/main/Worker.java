package passwordgenerator.main;
import java.math.BigInteger;
import java.security.MessageDigest;

public class Worker {

	public Worker() {
		System.out.println("[Worker] Creating");
	}
	
	public String Execute(String text) throws Exception {
		System.out.println("[Worker] Executing");
		
		if (text.isEmpty()) {
			System.out.println("[Worker] Empty string");
			return "";
		}
		
		String s = text;
		
		MessageDigest m = MessageDigest.getInstance("MD5");
		
		m.update(s.getBytes(), 0, s.length());
		
		String hash = new BigInteger(1, m.digest()).toString(16);
		
		System.out.println("MD5: " + hash);
		
		return hash;
	}
}
