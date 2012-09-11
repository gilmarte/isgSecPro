package com.isg.ifrend.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * TODO This class provides routines for dealing with strings.
 * 
 * @author kristine.furto
 * 
 */
public class StringUtil {

	/**
	 * Returns true if the string is null or just whitespace
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return (s == null || s.trim().length() == 0);
	}

	/**
	 * For Display: add prefix XXXX before 000n
	 * 
	 * @param srcStr
	 * @param length
	 * @param padBy
	 * @return
	 */
	public static String lPad(String prefixStr, String srcStr, int length,
			String padBy) {

		StringBuffer resultBuf = new StringBuffer("");
		resultBuf.append(prefixStr);
		for (int i = 0; i < (length - srcStr.length()); i++) {
			resultBuf.append(padBy);
		}
		resultBuf.append(srcStr);

		return resultBuf.toString();
	}

	public static String lPad(String srcStr, int length, String padBy) {

		StringBuffer resultBuf = new StringBuffer("");
		for (int i = 0; i < (length - srcStr.length()); i++) {
			resultBuf.append(padBy);
		}
		resultBuf.append(srcStr);

		return resultBuf.toString();
	}

	/**
	 * For Display: add suffix 000n after XXXX
	 * 
	 * @param srcStr
	 * @param length
	 * @param padBy
	 * @return
	 */
	public static String rPad(String srcStr, int length, String padBy) {
		StringBuffer resultBuf = new StringBuffer(srcStr);
		for (int i = 0; i < (length - srcStr.length()); i++) {
			resultBuf.append(padBy);
		}
		return resultBuf.toString();
	}

	/**
	 * Removes characters from the String
	 * 
	 * @param src
	 * @param toRemove
	 * @return
	 */
	public static String removeString(String src, String toRemove) {
		return src.replaceAll(toRemove, "");
	}

	public static boolean isEmpty(String[] s) {
		if (s == null)
			return true;
		for (int i = 0; i < s.length; i++) {
			String string = s[i];
			if (!isEmpty(string))
				return false;

		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	public static String join(String[] values, String delim) {
		if (values == null) {
			return join((Collection) null, delim);
		} else {
			return join(Arrays.asList(values), delim);
		}
	}

	@SuppressWarnings("rawtypes")
	public static String join(Collection values, String delim) {
		StringBuffer buf = new StringBuffer("");

		if (values != null) {
			for (Iterator i = values.iterator(); i.hasNext();) {
				Object value = i.next();
				String str = value != null ? value.toString() : "null";

				buf.append(str);
				if (i.hasNext()) {
					buf.append(delim);
				}
			}
		}

		return buf.toString();
	}

	public static String capitalize(String str) {
		StringBuffer res;

		if (str.length() == 0)
			return "";

		res = new StringBuffer();
		res.append(Character.toUpperCase(str.charAt(0)));

		if (str.length() > 1) {
			res.append(str.substring(1));
		}

		return res.toString();
	}

	/**
	 * Returns whether characters are only letters and digits.
	 * 
	 * @param str
	 * @param validCharacters
	 * @return
	 */
	public static boolean isAlphaNumeric(String str, char[] validCharacters) {

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!Character.isLetterOrDigit(c)) {
				boolean foundExtraCharacters = false;
				for (int j = 0; j < validCharacters.length; j++) {
					if (validCharacters[j] == c) {
						foundExtraCharacters = true;
					}
				}
				if (!foundExtraCharacters)
					return false;

			}
		}
		return true;
	}

}
