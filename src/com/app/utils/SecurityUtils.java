package com.app.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;

/**
 * MD5,sha1,sha256,sha384,sha512加密;base64,url加解密
 * 
 * @author ZF
 * @2010-11-21
 */
public class SecurityUtils {

	/**
	 * 采用MD5加密;单向加密
	 * 
	 * @param source
	 * @return
	 */
	public static String getMD5(String source) {
		return DigestUtils.md5Hex(source);
	}

	/**
	 * 采用Sha1加密;单向加密
	 * 
	 * @param source
	 * @return
	 */
	public static String getSha1(String source) {
		return DigestUtils.sha1Hex(source);
	}

	/**
	 * 采用Sha256加密;单向加密
	 * 
	 * @param source
	 * @return
	 */
	public static String getSha256(String source) {
		return DigestUtils.sha256Hex(source);
	}

	/**
	 * 采用Sha384加密;单向加密
	 * 
	 * @param source
	 * @return
	 */
	public static String getSha384(String source) {
		return DigestUtils.sha384Hex(source);
	}

	/**
	 * 采用Sha512加密;单向加密
	 * 
	 * @param source
	 * @return
	 */
	public static String getSha512(String source) {
		return DigestUtils.sha512Hex(source);
	}

	/************************************************ 对称加密 ********************************************/
	/**
	 * 采用Base64加密，不是安全算法，适用一般加密
	 * 
	 * @param source
	 * @return
	 */
	public static String encodeBase64(String source) {
		return Base64.encodeBase64String(source.getBytes());
	}

	/**
	 * 采用Base64解密
	 * 
	 * @param source
	 * @return
	 */
	public static String decodeBase64(String source) {
		return new String(Base64.decodeBase64(source));
	}

	/**
	 * url编码
	 * 
	 * @param url
	 * @return
	 */
	public static String encodeUrl(String url) {
		String codeUrl = "";
		try {
			URLCodec codec = new URLCodec();
			codeUrl = codec.encode(url);
		} catch (EncoderException e) {
			e.printStackTrace();
		}
		return codeUrl;
	}

	/**
	 * url解码
	 * 
	 * @param url
	 * @return
	 */
	public static String decodeUrl(String url) {
		String codeUrl = "";
		try {
			URLCodec codec = new URLCodec();
			codeUrl = codec.decode(url);
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return codeUrl;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(SecurityUtils.encodeBase64("11000.00"));
		System.out.println(SecurityUtils.decodeBase64("MTEwMDAuMDA="));
		System.out.println(SecurityUtils.encodeUrl("http://www.iteye.com/topic/1122076"));
		System.out.println(SecurityUtils.decodeUrl("http%3A%2F%2Fwww.iteye.com%2Ftopic%2F1122076"));

		/*
		 * System.out.println(SecurityUtils.getMD5("888888"));
		 * System.out.println(SecurityUtils.getSha1("888888"));
		 * System.out.println(SecurityUtils.getSha256("888888"));
		 * System.out.println(SecurityUtils.getSha384("888888"));
		 * System.out.println(SecurityUtils.getSha512("888888"));
		 * 
		 * System.out.println(SecurityUtils.getMD5("888888").length());
		 * System.out.println(SecurityUtils.getSha1("888888").length());
		 * System.out.println(SecurityUtils.getSha256("888888").length());
		 * System.out.println(SecurityUtils.getSha384("888888").length());
		 * System.out.println(SecurityUtils.getSha512("888888").length());
		 */
	}

}
