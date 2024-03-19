package Personnel_Attend;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;


@Component
public class PasswordEncryption {

	public String hashedPassword(String password) {
		String HashPassword=BCrypt.hashpw(password, BCrypt.gensalt(12));
		return HashPassword;
	}
	
	private boolean verifyPsasword(String password, String hashedPassword) {
		return BCrypt.checkpw(password,hashedPassword);
	}
	
	
	public boolean Password_Check (String password,String HashPassword) {
		boolean x=verifyPsasword(password,HashPassword);
        System.out.println("PasswordBolean: " + x);
        return x;
	}
}
