package com.ischoolbar.programmer.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * �ַ������ò�����
 * @author llq
 *
 */
public class StringUtil {
	
	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str == null || "".equals(str))return true;
		return false;
	}
	
	/**
	 * ���շ��������ַ���ת��Ϊ�»����ַ���
	 * @param str
	 * @return
	 */
	public static String convertToUnderline(String str){
		if(isEmpty(str))return null;
		String ret = "";
		for(int i=0; i< str.length();i++){
			char charAt = str.charAt(i);
			if(Character.isUpperCase(charAt)){
				if(i == 0){
					ret += String.valueOf(charAt).toLowerCase();
					continue;
				}
				ret += "_" + String.valueOf(charAt).toLowerCase();
				continue;
			}
			ret += charAt;
		}
		return ret;
	}
	
	/**
	 * ����ָ�����ַ�������ָ���ַ���
	 * @param list
	 * @param split
	 * @return
	 */
	public static String join(List<String> list,String split){
		String ret = "";
		for(Object object : list){
			ret += object + split;
		}
		ret = ret.substring(0,ret.lastIndexOf(split));
		return ret;
	}
	
	/**
	 * ��ָ���ַ���д��HttpServletResponse
	 * @param response
	 * @param content
	 */
	public static void writrToPage(HttpServletResponse response,String content){
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
