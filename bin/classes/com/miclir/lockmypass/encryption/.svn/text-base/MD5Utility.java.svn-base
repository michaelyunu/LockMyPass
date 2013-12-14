/**
 * 
 */

package com.miclir.lockmypass.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Log;

/**
 * @author Michael & Liron
 * 
 */
public class MD5Utility
{
	
	private static final String	TAG	= "MD5Utility";
	
	
	public static String getMD5(String in)
	{
		MessageDigest digest;
		try
		{
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(in.getBytes());
			byte [] a = digest.digest();
			int len = a.length;
			StringBuilder sb = new StringBuilder(len << 1);
			for(int i = 0 ; i < len ; i++)
			{
				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
				sb.append(Character.forDigit(a[i] & 0x0f, 16));
			}
			return sb.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
