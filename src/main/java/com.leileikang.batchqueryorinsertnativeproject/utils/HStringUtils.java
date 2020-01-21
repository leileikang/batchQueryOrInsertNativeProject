package com.leileikang.batchqueryorinsertnativeproject.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 
 * @ClassName: HStringUtils
 * @Description: 字符串工具类
 * @author Administrator
 * @date 2020/1/21
 *
 */
public class HStringUtils extends StringUtils {
	/**
	 * 字符串转成时间,最小值
	 * 
	 * @param str
	 * @return
	 */
	public static Date minStringDate(String str) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			str = str + " 00:00:00.000";
			date = formatter.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转成时间,最大值
	 * 
	 * @param str
	 * @return
	 */
	public static Date maxStringDate(String str) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			str = str + " 23:59:59.000";
			date = formatter.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据当前时间获取前某一天之间的String数组
	 * 
	 * @param day
	 * @return
	 */
	public static String[] getSpellDate(int day) {
		String[] date = new String[day];
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		Calendar c = Calendar.getInstance();
		for (int i = 0; i < day; i++) {
			c.setTime(new Date());
			c.add(Calendar.DATE, -i);
			Date d = c.getTime();
			date[i] = format.format(d);
		}
		return date;
	}

	/**
	 * @Description: 首字母大写
	 * @param name
	 * @return
	 * @author tkk
	 * @date 2017年6月9日 上午9:53:27
	 */
	public static String firstUpperName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);

	}

	/**
	 * @Description: 是否是空（包含“null”为空判断 ）
	 * @param str
	 * @return
	 * @author tkk
	 * @date 2017年5月23日 上午11:21:04
	 */
	public static boolean isblank(String str) {
		String strs = "null";
		if (isBlank(str) || strs.equalsIgnoreCase(str)) {
			return true;
		}
		return false;
	}

	/**
	 * @Description: null/" "等转换为""
	 * @param str
	 * @return
	 * @author tkk
	 * @date 2017年5月23日 上午11:33:10
	 */
	public static String null2Empty(String str) {
		if (isblank(str)) {
			return "";
		}
		return str;
	}

	/**
	 * @Description: “ture","false"转为1,0
	 * @param str
	 * @return
	 * @date 2017年5月17日 下午1:48:27
	 */
	public static Integer true2integer(String str) {
		String s1 = "ture";
		String s2 = "false";
		if (!isblank(str)) {
			if (s1.equalsIgnoreCase(str)) {
				return Integer.valueOf(1);
			} else if (s2.equalsIgnoreCase(str)) {
				return Integer.valueOf(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 数字转字符串,如果num<=0 则输出"";
	 * 
	 * @param num
	 * @return
	 */
	public static String numberToString(Object num) {
		if (num == null) {
			return null;
		} else if (num instanceof Integer && (Integer) num > 0) {
			return Integer.toString((Integer) num);
		} else if (num instanceof Long && (Long) num > 0) {
			return Long.toString((Long) num);
		} else if (num instanceof Float && (Float) num > 0) {
			return Float.toString((Float) num);
		} else if (num instanceof Double && (Double) num > 0) {
			return Double.toString((Double) num);
		} else {
			return "";
		}
	}

	/**
	 * 判断这个类是不是java自带的类
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isJavaClass(Class<?> clazz) {
		boolean isBaseClass = false;
		String keyClass = "java.lang";
		if (clazz.isArray()) {
			isBaseClass = false;
		} else if (clazz.isPrimitive() || clazz.getPackage() == null || keyClass.equals(clazz.getPackage().getName())
				|| "java.math".equals(clazz.getPackage().getName())
				|| "java.util".equals(clazz.getPackage().getName())) {
			isBaseClass = true;
		}
		return isBaseClass;
	}

	/**
	 * 首字母小写
	 * 
	 * @param realName
	 * @return
	 */
	public static String firstLowerCase(String realName) {
		if (HStringUtils.isblank(realName)) {
			return null;
		}
		char[] charArray = realName.toCharArray();
		char c = 'A';
		char d = 'Z';
		if (charArray[0] >= c && charArray[0] <= d) {
			charArray[0] += 32;
		}
		return String.valueOf(charArray);
	}

	/**
	 * @Description: 对象是否空
	 * @param obj
	 * @return
	 * @author tkk
	 * @date 2017年5月23日 下午3:53:30
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	public static String getUUID() {
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString().replace("-", "");
	}

/*	public static void main(String[] args) {
		System.out.println(minStringDate("2019-02-01"));
	}*/
}
