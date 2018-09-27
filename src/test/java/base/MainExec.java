package base;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.lang3.RandomStringUtils;

public class MainExec {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		/*SecureRandom sr = SecureRandom.getInstance("sha256");
		byte[] bytes =new byte[200];
		sr.nextBytes(bytes);
		for (byte b : bytes) {
			System.out.println((char)b);
		}*/
		int[]  m = new int[1000];
		for(int i=1 ;i<500;i++){
			Integer v = Integer.valueOf(RandomStringUtils.random(1, "123")+RandomStringUtils.randomNumeric(2));
			//if(RandomStringUtils.randomNumeric(1).equals("1")){
				m[v]++;
			//}
		}
		for (int i = 0; i < m.length; i++) {
		if(m[i]!=0){
			System.out.println(i+"-->"+m[i]);
		}
		}
	}
}
