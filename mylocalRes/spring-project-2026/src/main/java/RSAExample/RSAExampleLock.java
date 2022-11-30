package RSAExample;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

public class RSAExampleLock {
	 private final static String ALGORITHM = "RSA";
	    private final static String CHARSET   = "utf-8";

	    /**
	     * 密钥长度 于原文长度对应 以及越长速度越慢
	     */
	    private final static int KEY_SIZE = 512;

	    private static PublicKey  publicKey;
	    private static PrivateKey privateKey;

	    /**
	     * 随机生成密钥对
	     */
	    public static void genKeyPair() throws NoSuchAlgorithmException {
	        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
	        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
	        KeyPair keyPair = keyPairGen.generateKeyPair();
	        publicKey  = keyPair.getPublic();
	        privateKey = keyPair.getPrivate();
	    }

	    public static byte[] encrypt(String str, PublicKey pubKey) throws Exception {
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

	        return cipher.doFinal(str.getBytes(CHARSET));
	    }

	    public static byte[] decrypt(byte[] inputByte, PrivateKey priKey) throws Exception {
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.DECRYPT_MODE, priKey);
	        return cipher.doFinal(inputByte);
	    }

	   

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		long temp = System.currentTimeMillis();
        //生成公钥和私钥
        genKeyPair();
        System.out.println("生成密钥消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");

        String message = "5155";

        System.out.println("原文:" + message);
        temp = System.currentTimeMillis();
        byte[] messageEn = encrypt(message, publicKey);
        System.out.println("密文:" + messageEn.length);
        System.out.println("加密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");

        temp = System.currentTimeMillis();
        byte[] messageDe = decrypt(messageEn, privateKey);
        System.out.println("解密:" + new String(messageDe));
        System.out.println("解密消耗时间:" + (System.currentTimeMillis() - temp) / 1000.0 + "秒");
	}

}
