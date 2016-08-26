package com.yijiajiao.finance.util;

import java.util.Random;

public class RandomUtil {
	/**
	 *@description 获得给定长度的随机数字字符串
	 *@date 2016-2-17
	 *@return String
	 *@param length
	 */
    public static String getRandomCharNum(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
		    buffer.append(chr[random.nextInt(10)]);
		}
		return buffer.toString();
    }
}
