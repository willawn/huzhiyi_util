package com.huzhiyi.util;

import java.util.Date;
import java.util.Random;

public class MathUtils {

	/**
	 * @Title: getRandomNumber
	 * @Description: 获取指定范围的随机数
	 * 		<p>
	 * @author willter
	 * @date 2013-5-29
	 * 		<p>
	 * @param beginNumber
	 * @param endNumber
	 * @return
	 */
	public static Integer getRandomNumber(Integer beginNumber, Integer endNumber) {
		Date d = new Date();
		long lseed = d.getTime();
		Random r = new Random(lseed); // 设置随机种子
		Integer randomNumber = r.nextInt(endNumber + 1);
		if (randomNumber < beginNumber) {
			randomNumber = beginNumber;//getRandomNumber(beginNumber, endNumber);
		}
		return randomNumber;
	}
	
	public static void main(String[] args) {
		System.out.println("==============" + getRandomNumber(8, 15));
	}
}
