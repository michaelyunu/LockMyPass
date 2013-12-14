
package com.miclir.lockmypass.utils;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Michael & Liron
 * 
 */
public class PasswordGenerator
{
	
	public static String getGeneratedPassword(int legnth, boolean useLetters, boolean useNumbers, boolean useUpperCase, boolean useSpecialCharacters)
	{
		final int USE_LETTERS = 0;
		final int USE_UPPERCASE = 1;
		final int USE_NUMBERS = 2;
		final int USE_SPECIAL_CHARACTERS = 3;
		
		final ArrayList < Integer > cases = new ArrayList < Integer >(4);
		
		StringBuffer buffer = null;
		String [] lowerCaseLetters = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		String [] upperCaseLetters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		String [] numbers = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String [] specialCharacters = { "!", "@", "#", "$", "%", "&", "*", ".", "?", "(", ")", "{", "}", "\\", "|", "-", "[", "]", "," };
		
		if ( !(useLetters || useNumbers || useUpperCase || useSpecialCharacters) )
		{
			return null;
		}
		
		if ( useLetters )
		{
			cases.add(new Integer(USE_LETTERS));
		}
		if ( useNumbers )
		{
			cases.add(new Integer(USE_NUMBERS));
		}
		if ( useUpperCase )
		{
			cases.add(new Integer(USE_UPPERCASE));
		}
		if ( useSpecialCharacters )
		{
			cases.add(new Integer(USE_SPECIAL_CHARACTERS));
		}
		
		Random random = new Random();
		int randCase;
		buffer = new StringBuffer();
		for(int i = 0 ; i < legnth ; i++)
		{
			randCase = random.nextInt(cases.size());
			String str;
			switch( cases.get(randCase) )
			{
				case USE_LETTERS :
					int lenghtLower = lowerCaseLetters.length;
					str = lowerCaseLetters[random.nextInt(lenghtLower)];
					buffer.append(str);
					break;
				case USE_NUMBERS :
					int lenghtNumbers = numbers.length;
					str = numbers[random.nextInt(lenghtNumbers)];
					buffer.append(str);
					break;
				case USE_UPPERCASE :
					int lenghtUpper = upperCaseLetters.length;
					str = upperCaseLetters[random.nextInt(lenghtUpper)];
					buffer.append(str);
					break;
				case USE_SPECIAL_CHARACTERS :
					int lenghtChar = specialCharacters.length;
					str = specialCharacters[random.nextInt(lenghtChar)];
					buffer.append(str);
					break;
			}
		}
		
		return buffer.toString();
	}
}
