package manager;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

class Crypter {

	private String pw;
	private String salt = "fuckyoubase64";
	private SecretKey secret;
	
	public Crypter(String pw) {
		super();
		this.pw = pw;
	}
	
	private byte[] getSalt() {
		byte[] salt = this.salt.getBytes();
		return salt;
	}

	private char[] getPassword() {
		char[] password = this.pw.toCharArray();
		return password;
	}
	
	private void setSecret(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
		SecretKey tmp = factory.generateSecret(spec);
		this.secret = new SecretKeySpec(tmp.getEncoded(), "AES");
	}
	
	String encryptText(String text) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException  {
		char[] password = this.getPassword();
		byte[] salt = this.getSalt();
		this.setSecret(password, salt);
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, this.secret);
		AlgorithmParameters params = cipher.getParameters();
		
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		byte[] ciphertext = cipher.doFinal(text.getBytes("UTF-8"));
		
		String data = new String(iv) + "###" + new String(Base64.getEncoder().encodeToString(ciphertext));
		
		return data;
	}
	
	String decryptText(String siv, String ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		char[] password = this.getPassword();
		byte[] salt = this.getSalt();
		this.setSecret(password, salt);
		
		byte[] iv = siv.getBytes();
		byte[] text = Base64.getDecoder().decode(ciphertext);
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, this.secret, new IvParameterSpec(iv));
		String plaintext = new String(cipher.doFinal(text), "UTF-8");
		
		return plaintext;
	}
}
