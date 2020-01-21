package com.leileikang.batchqueryorinsertnativeproject.utils;

import java.util.UUID;

public class UUIDUtil {

	/**
	 * 获得指定数目的UUID
	 *
	 * @param number
	 *            int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] retArray = new String[number];
		for (int i = 0; i < number; i++) {
			retArray[i] = getUUID();
		}
		return retArray;
	}

	/**
	 * 获得一个UUID
	 *
	 * @return String UUID
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		// 去掉“-”符号
		return uuid.replaceAll("-", "");
	}
	/**
	 * 有序生成代号
	 * @param code
	 * @return
	 */
	public static String newCode(String code){
		if(code != null){
			Integer numCode = Integer.parseInt(code.substring(code.length()-2, code.length()));
			numCode+=1;
			code = code.substring(0, code.length()-2);
			if((numCode+"").length()==2){
				return code + "" + numCode;
			}else{
				return code + "0" + numCode + "";
			}
		}else{
			throw new Error("同级子分类 分类编号 为空，无法自动生成分类编号。");
		}
	}

}
