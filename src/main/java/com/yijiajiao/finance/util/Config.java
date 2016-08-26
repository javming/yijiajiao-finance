package com.yijiajiao.finance.util;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public class Config {
	/**
	 * 
	 * @author davis.shi
	 */
	private static final ResourceBundle config = ResourceBundle
			.getBundle("conf.config");

	/**
     *
     */
	private Config() {
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return new String(config.getString(key).getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return config.getString(key);
		}
	}

	public static int getInt(String key) {
		String temp = config.getString(key);
		int value = Integer.parseInt(temp);
		return value;
	}

	public static double getDouble(String key) {
		String temp = config.getString(key);
		double value = Double.parseDouble(temp);
		return value;
	}
}