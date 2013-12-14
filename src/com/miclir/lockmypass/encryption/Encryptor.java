
package com.miclir.lockmypass.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import android.util.Base64;

/**
 * 
 * @author Michael & Liron
 * 
 */
public class Encryptor implements IEncryptor
{
	
	/**
	 * 
	 */
	private static final String	ENCRYPTION_ALGORITHM	= "DES";

	private static Encryptor	instance	= null;
	
	Cipher						ecipher;
	
	Cipher						dcipher;
	
	SecretKey					key;
	
	
	/**
	 * 
	 * @param secret
	 *            String value indicating the raw data that will be manipulated using the DES
	 *            enctyption. This will descibe the DES key later
	 * @return instance of the Encryptor class
	 * @throws Exception
	 */
	public static Encryptor getInstance(String secret) throws Exception
	{
		if ( instance == null )
		{
			instance = new Encryptor(secret);
		}
		return instance;
	}
	
	
	private Encryptor(String secret) throws Exception
	{
		byte [] data = secret.getBytes("UTF-8");
		DESKeySpec desKeySpec = new DESKeySpec(data);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM);
		key = keyFactory.generateSecret(desKeySpec);
		
		ecipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		dcipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		ecipher.init(Cipher.ENCRYPT_MODE, key);
		dcipher.init(Cipher.DECRYPT_MODE, key);
	}
	
	
	@Override
	public String encrypt(String str) throws Exception
	{
		
		// Encode the string into bytes using utf-8
		byte [] utf8 = str.getBytes("UTF8");
		
		// Encrypt
		byte [] enc = ecipher.doFinal(utf8);
		// Encode bytes to base64 to get a string
		return Base64.encodeToString(enc, Base64.DEFAULT);
	}
	
	
	@Override
	public String decrypt(String str) throws Exception
	{
		// Decode base64 to get bytes
		byte [] dec = Base64.decode(str, Base64.DEFAULT);
		
		byte [] utf8 = dcipher.doFinal(dec);
		
		// Decode using utf-8
		return new String(utf8, "UTF8");
	}
	
}
