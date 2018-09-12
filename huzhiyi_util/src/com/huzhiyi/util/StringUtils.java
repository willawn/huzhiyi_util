/*
 * @(#)StringUtils.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.huzhiyi.model.abstraction.ValueSection;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @ClassName: StringUtils
 * @Description: TODO(字符串操作辅助类)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class StringUtils {
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	/**
	 * 空白字符
	 */
	public static final char EMPTY_CHAR = 31;
	/**
	 * 常用字符码
	 */
	public static final char[] COMMON_CHART_CODES = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	/**
	 * 常用字符码大小
	 */
	public static final int COMMON_CHART_CODES_SIZE = 61;

	private static final String ENCRYPT_DEFAULT_PROVIDER = "DES";

	// /
	// /加密算法提供
	// /
	private static MessageDigest mdSHA = null;
	private static MessageDigest mdMD5 = null;
	static {
		try {
			mdMD5 = MessageDigest.getInstance("MD5");
			mdSHA = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title: toInteger
	 * @Description: 字符串数组转换整形数组
	 * 		<p>
	 * @author willter
	 * @date 2013-3-15
	 * 		<p>
	 * @param strs
	 * @return
	 */
	public static Integer[] toInteger(String[] strs) {
		if (strs == null) {
			return null;
		}
		Integer[] ints = new Integer[strs.length];
		for (int i = 0, len = strs.length; i < len; i++) {
			ints[i] = Integer.parseInt(strs[i]);
		}
		return ints;
	}

	/**
	 * 去除字符（所有）
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String trimAll(String str, char trimChar) {
		if (str == null)
			return str;
		
		char trimChars[] = str.toCharArray();
		int i = 0;
		for (int len = trimChars.length; i < len; i++)
			if (trimChars[i] == trimChar)
				trimChars[i] = '\037';

		return new String(trimChars);
	}

	/**
	 * 去除字符所有的空白
	 * 
	 * @param str
	 * @return
	 */
	public static String trimAll(String str) {
		return trimAll(str, ' ');
	}

	/**
	 * 去除所有匹配的字符
	 * 
	 * @param str
	 * @param trimStr
	 * @return
	 */
	public static String trimAll(String str, String trimStr) {
		if (str == null)
			return str;
		
		return str.replaceAll(trimStr, "");
	}

	/**
	 * 去除前后匹配的字符
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String trim(String str, char trimChar) {
		if (str == null)
			return str;
		
		char val[] = str.toCharArray();
		int len = val.length;
		int st = 0;
		int off;
		for (off = 0; st < len && val[off + st] == trimChar; st++)
			;
		for (; st < len && val[(off + len) - 1] == trimChar; len--)
			;
		return st <= 0 && len >= len ? str : str.substring(st, len);
	}

	/**
	 * 去除左边匹配的字符
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String leftTrim(String str, char trimChar) {
		if (str == null)
			return str;
		
		char trimChars[] = str.toCharArray();
		int i = 0;
		for (int len = trimChars.length; i < len; i++) {
			if (trimChars[i] != trimChar)
				break;
			trimChars[i] = '\037';
		}

		return new String(trimChars);
	}

	/**
	 * 去除左边匹配的字符串
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String leftTrim(String str, String targetStr) {
		if (str == null)
			return str;
		
		if (str.startsWith(targetStr)) {
			String tmp = str.substring(targetStr.length());
			return leftTrim(tmp, targetStr);
		} else {
			return str;
		}
	}

	/**
	 * 去除左边空白
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String leftTrim(String str) {
		return leftTrim(str, ' ');
	}

	/**
	 * 去除后边匹配的字符
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String rightTrim(String str, char trimChar) {
		char trimChars[] = str.toCharArray();
		for (int i = trimChars.length - 1; i >= 0; i--) {
			if (trimChars[i] != trimChar)
				break;
			trimChars[i] = '\037';
		}

		return new String(trimChars);
	}

	/**
	 * 去除右边匹配的字符串
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String rightTrim(String str, String trimStr) {
		if (str.endsWith(trimStr)) {
			String tmp = str.substring(0, str.length() - trimStr.length());
			return rightTrim(tmp, trimStr);
		} else {
			return str;
		}
	}

	/**
	 * 去除右边匹配的字符
	 * 
	 * @param str
	 * @param trimChar
	 * @return
	 */
	public static String rightTrim(String str) {
		return rightTrim(str, ' ');
	}

	/**
	 * 省略的字符串
	 * 
	 * @param str
	 * @param omitStr
	 * @param len
	 * @return
	 */
	public static String omitString(String str, String omitStr, int len) {
		if (str.length() > len)
			return str.substring(0, len).concat(omitStr);
		else
			return str;
	}

	/**
	 * 省略的字符串
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String omitString(String str, int len) {
		if (str.length() > len)
			return str.substring(0, len).concat("......");
		else
			return str;
	}

	/**
	 * 把字符数据并和成字符串显示
	 * 
	 * @param byteArr
	 * @return
	 */
	public static String getString(byte byteArr[]) {
		StringBuffer sb = new StringBuffer(byteArr.length);
		for (byte b : byteArr) {
			sb.append(b);
		}
		return sb.toString();
	}

	/**
	 * 使用MD5加密
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static String toMD5(String input) {
		return toHexString(mdMD5.digest(input.getBytes()));
	}

	/**
	 * 使用SHA加密
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static String toSHA(String input) {
		return toHexString(mdSHA.digest(input.getBytes()));
	}

	/**
	 * 加密字符串
	 * 
	 * @param input
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptString(String input, byte key[]) throws Exception {
		SecretKeySpec sk = new SecretKeySpec(key, ENCRYPT_DEFAULT_PROVIDER);
		Cipher c = Cipher.getInstance(ENCRYPT_DEFAULT_PROVIDER);
		c.init(Cipher.ENCRYPT_MODE, sk);
		return toHexString(c.doFinal(input.getBytes()));
	}

	/**
	 * 解密字符串
	 * 
	 * @param input
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptString(String input, byte key[]) throws Exception {
		SecretKeySpec sk = new SecretKeySpec(key, ENCRYPT_DEFAULT_PROVIDER);
		Cipher c = Cipher.getInstance(ENCRYPT_DEFAULT_PROVIDER);
		c.init(Cipher.DECRYPT_MODE, sk);
		return new String(c.doFinal(fromHexString(input)));
	}

	/**
	 * 加密字符串
	 * 
	 * @param input
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(String input, byte key[]) throws Exception {
		SecretKeySpec sk = new SecretKeySpec(key, ENCRYPT_DEFAULT_PROVIDER);
		Cipher c = Cipher.getInstance(ENCRYPT_DEFAULT_PROVIDER);
		c.init(Cipher.ENCRYPT_MODE, sk);
		return c.doFinal(input.getBytes());
	}

	/**
	 * 解密字符串
	 * 
	 * @param input
	 * @param key
	 * @return
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 */
	public static String decrypt(byte input[], byte key[]) throws Exception {
		SecretKeySpec sk = new SecretKeySpec(key, ENCRYPT_DEFAULT_PROVIDER);
		Cipher c = Cipher.getInstance(ENCRYPT_DEFAULT_PROVIDER);
		c.init(Cipher.DECRYPT_MODE, sk);
		return new String(c.doFinal(input));
	}

	/**
	 * 使用Base64编码
	 * 
	 * @param str
	 * @return
	 */
	public static String toBase64(String str) {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(str.getBytes());
	}

	/**
	 * 使用Base64解码
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static String fromBase64(String str) {
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			return new String(decoder.decodeBuffer(str));
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 转化成16进制表示
	 * 
	 * @param byteArr
	 * @return
	 */
	public static String toHexString(byte byteArr[]) {
		StringBuilder buf = new StringBuilder();
		byte abyte0[];
		int j = (abyte0 = byteArr).length;
		for (int i = 0; i < j; i++) {
			byte b = abyte0[i];
			long tmp = b & 255;
			if (tmp < 16L)
				buf.append("0");
			buf.append(Long.toHexString(tmp));
		}

		return buf.toString();
	}

	/**
	 * 从十六进制的字符转化为字节数组
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] fromHexString(String s) {
		int stringLength = s.length();
		if ((stringLength & 0x1) != 0) {
			throw new IllegalArgumentException("fromHexString   requires   an   even   number   of   hex   characters");
		}
		byte[] b = new byte[stringLength / 2];
		for (int i = 0, j = 0; i < stringLength; i += 2, j++) {
			int high = charToNibble(s.charAt(i));
			int low = charToNibble(s.charAt(i + 1));
			b[j] = (byte) ((high << 4) | low);
		}
		return b;
	}

	private static int charToNibble(char c) {
		if ('0' <= c && c <= '9') {
			return c - '0';
		} else if ('a' <= c && c <= 'f') {
			return c - 'a' + 0xa;
		} else if ('A' <= c && c <= 'F') {
			return c - 'A' + 0xa;
		} else {
			throw new IllegalArgumentException("Invalid   hex   character:   " + c);
		}
	}

	/**
	 * 转化成16进制表示
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte b[]) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 255);
			if (stmp.length() == 1)
				hs = hs.concat("0").concat(stmp);
			else
				hs = hs.concat(stmp);
			if (n < b.length - 1)
				hs = hs.concat(":");
		}

		return hs.toUpperCase();
	}

	/**
	 * 对字符串编码
	 * 
	 * @param str
	 * @param charsetName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String str, String charsetName) throws UnsupportedEncodingException {
		byte bytes[] = str.getBytes(charsetName);
		return new String(bytes);
	}

	/**
	 * URL字符串编码
	 * 
	 * @param str
	 * @param charsetName
	 * @return
	 */
	public static String urlEncode(String str, String charsetName) throws UnsupportedEncodingException {
		return URLEncoder.encode(str, charsetName);
	}

	/**
	 * 对字符串编码
	 * 
	 * @param str
	 * @param charsetName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String str, String charsetName) throws UnsupportedEncodingException {
		Charset cs = Charset.forName(charsetName);
		ByteBuffer bb = ByteBuffer.wrap(str.getBytes());
		CharBuffer cb = cs.decode(bb);
		StringBuilder buf = new StringBuilder(cb.length());
		buf.append(cb);
		return buf.toString();
	}

	/**
	 * 对字符串解码
	 * 
	 * @param str
	 * @param charsetName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlDecode(String str, String charsetName) throws UnsupportedEncodingException {
		return URLDecoder.decode(str, charsetName);
	}

	/**
	 * 字符串首个字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstToUpperCase(String str) {
		char trimChars[] = str.toCharArray();
		trimChars[0] = Character.toUpperCase(trimChars[0]);
		return new String(trimChars);
	}

	/**
	 * 字符串首个字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstToLowerCase(String str) {
		char trimChars[] = str.toCharArray();
		trimChars[0] = Character.toLowerCase(trimChars[0]);
		return new String(trimChars);
	}

	/**
	 * 验证字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 验证字符串是否非空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && str.trim().length() > 0;
	}

	/**
	 * 获取随机字符串（多用于生成随机密码或验证码）
	 * 
	 * @param length
	 *            字符串的长度
	 * @return
	 */
	public static String getRandomCharacters(int length) {
		char[] randomCharacters = new char[length];
		Random r = new Random();
		int index;
		int i = 0;
		for (; i < length;) {
			index = r.nextInt(COMMON_CHART_CODES_SIZE);
			randomCharacters[i] = COMMON_CHART_CODES[index];
			i++;
		}
		return new String(randomCharacters);
	}

	/**
	 * 截取字符，并截取后的首个字母小写
	 * 
	 * @param s
	 * @param start
	 * @return
	 */
	public static String subStrFirstLowerCase(String s, int start) {
		int sLen = s.length();
		int nLen = sLen - start;
		char[] chs = new char[nLen];
		chs[0] = Character.toLowerCase(s.charAt(start));
		for (int i = 1; i < nLen; i++) {
			chs[i] = s.charAt(i + start);
		}
		return new String(chs);
	}

	/**
	 * java实现不区分大小写替换
	 * 
	 * @param source
	 * @param oldstring
	 * @param newstring
	 * @return
	 */
	public static String ignoreCaseReplace(String source, String oldstring, String newstring) {
		Pattern p = Pattern.compile(oldstring, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(source);
		String ret = m.replaceAll(newstring);
		return ret;
	}

	/**
	 * 将字符串编码成 Unicode 。
	 * 
	 * @param theString
	 *            待转换成Unicode编码的字符串。
	 * @param escapeSpace
	 *            是否忽略空格。
	 * @return 返回转换后Unicode编码的字符串。
	 */
	public static String toUnicode(String theString, boolean escapeSpace) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
				case ' ':
					if (x == 0 || escapeSpace)
						outBuffer.append('\\');
					outBuffer.append(' ');
					break;
				case '\t':
					outBuffer.append('\\');
					outBuffer.append('t');
					break;
				case '\n':
					outBuffer.append('\\');
					outBuffer.append('n');
					break;
				case '\r':
					outBuffer.append('\\');
					outBuffer.append('r');
					break;
				case '\f':
					outBuffer.append('\\');
					outBuffer.append('f');
					break;
				case '=': // Fall through
				case ':': // Fall through
				case '#': // Fall through
				case '!':
					outBuffer.append('\\');
					outBuffer.append(aChar);
					break;
				default:
					if ((aChar < 0x0020) || (aChar > 0x007e)) {
						outBuffer.append('\\');
						outBuffer.append('u');
						outBuffer.append(toHex((aChar >> 12) & 0xF));
						outBuffer.append(toHex((aChar >> 8) & 0xF));
						outBuffer.append(toHex((aChar >> 4) & 0xF));
						outBuffer.append(toHex(aChar & 0xF));
					} else {
						outBuffer.append(aChar);
					}
			}
		}
		return outBuffer.toString();
	}

	/**
	 * 从 Unicode 码转换成编码前的特殊字符串。
	 * 
	 * @param in
	 *            Unicode编码的字符数组。
	 * @param off
	 *            转换的起始偏移量。
	 * @param len
	 *            转换的字符长度。
	 * @param convtBuf
	 *            转换的缓存字符数组。
	 * @return 完成转换，返回编码前的特殊字符串。
	 */
	public String fromUnicode(char[] in, int off, int len, char[] convtBuf) {
		if (convtBuf.length < len) {
			int newLen = len * 2;
			if (newLen < 0) {
				newLen = Integer.MAX_VALUE;
			}
			convtBuf = new char[newLen];
		}
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = in[off++];
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char) value;
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = (char) aChar;
			}
		}
		return new String(out, 0, outLen);
	}

	/**
	 * 替换字符串
	 * 
	 * @param str
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
	public static String replace(String str, String oldStr, String newStr) {
		byte[] data = str.getBytes();
		data = replace(data, oldStr, newStr);
		return new String(data);
	}

	/**
	 * 替换字符
	 * 
	 * @param data
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
	public static byte[] replace(byte[] data, String oldStr, String newStr) {
		return replace(data, oldStr.getBytes(), newStr.getBytes());
	}

	/**
	 * 替换字节
	 * 
	 * @param data
	 * @param oldBytes
	 * @param newBytes
	 * @return
	 */
	public static byte[] replace(byte[] data, byte[] oldBytes, byte[] newBytes) {
		byte[] store = data;

		int len = store.length;
		int olen = oldBytes.length;
		int nlen = newBytes.length;

		int cnt = 0;
		for (int i = 0; i < len; i++) {
			if (store[i] == oldBytes[cnt]) {
				cnt++;
			} else {
				cnt = 0;
			}
			if (cnt == olen) {
				if (olen > nlen) {
					for (int j = 0; j < olen; j++) {
						int k = i - olen + 1 + j;
						if (j < nlen) {
							store[k] = newBytes[j];
						} else {
							store[k] = EMPTY_CHAR;
						}

					}
				} else {
					byte[] tmp = store;
					int n = nlen - olen;
					int ndlen = len + n;
					store = new byte[ndlen];
					int j = 0;
					for (; j < i + 1 - olen; j++) {
						store[j] = tmp[j];

					}
					int k = j;
					for (j = 0; j < nlen; j++) {
						store[j + k] = newBytes[j];
					}

					j = j + k;
					for (; j < ndlen; j++) {
						store[j] = tmp[j - n];
					}
					len = ndlen;

				}
				cnt = 0;
			}
		}
		return store;
	}

	/**
	 * 替换字节
	 * 
	 * @param data
	 * @param oldBytes
	 * @param newBytes
	 */
	public static byte[] replace(byte[] data, Map<String, String> rpMap) {
		byte[] store = data;
		Set<String> keySet = rpMap.keySet();
		byte[] oldBytes, newBytes;
		for (String key : keySet) {
			oldBytes = key.getBytes();
			newBytes = rpMap.get(key).getBytes();
			store = replace(store, oldBytes, newBytes);
		}
		return store;
	}

	/**
	 * 截取字符串
	 * 
	 * @param source
	 * @param beginStr
	 * @param endStr
	 * @return
	 */
	public static String subStr(String source, String beginStr, String endStr) {
		byte[] beginBytes = beginStr.getBytes();
		byte[] endBytes = endStr.getBytes();
		byte[] sourceBytes = source.getBytes();
		int slen = sourceBytes.length;
		int blen = beginBytes.length;
		int elen = endBytes.length;
		int bcnt = 0;
		int ecnt = 0;

		int begin = -1;

		for (int i = 0; i < slen; i++) {
			// 找开始位置
			if (begin == -1) {
				if (sourceBytes[i] == beginBytes[bcnt]) {
					bcnt++;
				} else {
					bcnt = 0;
				}
				// 如果找到了
				if (bcnt == blen) {
					begin = i + 1;
					bcnt = 0;
				}
			} else {
				// 找结束位置
				if (sourceBytes[i] == endBytes[ecnt]) {
					ecnt++;
				} else {
					ecnt = 0;
				}
				// 如果找到了
				if (ecnt == elen) {
					int end = i - elen + 1;
					ecnt = 0;
					return source.substring(begin, end);
				}
			}

		}

		return null;
	}

	/**
	 * 截取字符串
	 * 
	 * @param source
	 * @param beginStr
	 * @return
	 */
	public static String subStr(String source, String beginStr) {
		byte[] beginBytes = beginStr.getBytes();
		byte[] sourceBytes = source.getBytes();
		int slen = sourceBytes.length;
		int blen = beginBytes.length;
		int bcnt = 0;

		int begin = -1;

		for (int i = 0; i < slen; i++) {
			if (sourceBytes[i] == beginBytes[bcnt]) {
				bcnt++;
			} else {
				bcnt = 0;
			}
			// 如果找到了
			if (bcnt == blen) {
				begin = i + 1;
				return source.substring(begin);
			}
		}

		return null;
	}

	/**
	 * 截取字节
	 * 
	 * @param sourceBytes
	 * @param beginBytes
	 * @param endBytes
	 * @return
	 */
	public static byte[] subBytes(byte[] sourceBytes, byte[] beginBytes, byte[] endBytes) {
		return subBytes(0, sourceBytes, beginBytes, endBytes);
	}

	/**
	 * 截取字节
	 * 
	 * @param start
	 * @param sourceBytes
	 * @param beginBytes
	 * @param endBytes
	 * @return
	 */
	public static byte[] subBytes(int start, byte[] sourceBytes, byte[] beginBytes, byte[] endBytes) {
		int slen = sourceBytes.length;
		if (start >= slen) {
			throw new IndexOutOfBoundsException("开始索引超出源字节数组长度");
		}
		int blen = beginBytes.length;
		int elen = endBytes.length;
		int bcnt = 0;
		int ecnt = 0;

		int begin = -1;

		for (; start < slen; start++) {
			// 找开始位置
			if (begin == -1) {
				if (sourceBytes[start] == beginBytes[bcnt]) {
					bcnt++;
				} else {
					bcnt = 0;
				}
				// 如果找到了
				if (bcnt == blen) {
					begin = start + 1;
					bcnt = 0;
				}
			} else {
				// 找结束位置
				if (sourceBytes[start] == endBytes[ecnt]) {
					ecnt++;
				} else {
					ecnt = 0;
				}
				// 如果找到了
				if (ecnt == elen) {
					ecnt = 0;
					int end = start - elen + 1;
					int len = end - begin;
					byte[] bytes = new byte[len];
					for (int i = 0; i < len; i++) {
						bytes[i] = sourceBytes[begin + i];
					}
					return bytes;
				}
			}

		}
		return null;
	}

	/**
	 * 获得系统自动产生的加密钥匙
	 * 
	 * @return
	 */
	public static byte[] generateKey() {
		try {
			return KeyGenerator.getInstance(ENCRYPT_DEFAULT_PROVIDER).generateKey().getEncoded();
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	/**
	 * 获得系统自动产生的加密钥匙的字符串表现形式
	 * 
	 * @return
	 */
	public static String generateKeyString() {
		return toHexString(generateKey());
	}

	/**
	 * 把字符串的加密钥匙转化为字节组
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] generateKey(String key) {
		return fromHexString(key);
	}

	/**
	 * 查找字节
	 * 
	 * @param start
	 * @param sourceBytes
	 * @param beginBytes
	 * @param endBytes
	 * @return
	 */
	public static ValueSection<Integer> findBytes(int start, byte[] sourceBytes, byte[] beginBytes, byte[] endBytes) {
		int slen = sourceBytes.length;
		if (start >= slen) {
			throw new IndexOutOfBoundsException("开始索引超出源字节数组长度");
		}
		int blen = beginBytes.length;
		int elen = endBytes.length;
		int bcnt = 0;
		int ecnt = 0;

		int begin = -1;

		for (; start < slen; start++) {
			// 找开始位置
			if (begin == -1) {
				if (sourceBytes[start] == beginBytes[bcnt]) {
					bcnt++;
				} else {
					bcnt = 0;
				}
				// 如果找到了
				if (bcnt == blen) {
					begin = start + 1;
					bcnt = 0;
				}
			} else {
				// 找结束位置
				if (sourceBytes[start] == endBytes[ecnt]) {
					ecnt++;
				} else {
					ecnt = 0;
				}
				// 如果找到了
				if (ecnt == elen) {
					ecnt = 0;
					final int s = begin;
					final int e = start - elen + 1;
					return new ValueSection<Integer>() {

						public Integer getBegin() {
							return s;
						}

						public Integer getEnd() {
							return e;
						}

					};
				}
			}

		}
		return null;
	}

	/**
	 * 创建StringBuilder
	 * 
	 * @param objects
	 * @return
	 */
	public static StringBuilder stringBuilder(Object... objects) {
		StringBuilder buf = new StringBuilder();
		for (Object obj : objects) {
			buf.append(obj);
		}
		return buf;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// String en="UTF-8";
		// String s=encode("我们的哎是", en);
		// System.out.println(decode(s, en));
	}
}
