package com.lemon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat ymdFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat ymdhmsFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat ymdhmsFormat2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	//给我一个date，返回20180329：年月日
	/**
	 * 返回年月日：yyyyMMdd
	 * @param date
	 * @return
	 */
	public static String getYMDString(Date date){
		return ymdFormat.format(date);
	}
	/**
	 * 返回年月日：yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String getYMDString2(Date date){
		return ymdFormat2.format(date);
	}
	public static String getYmdHmsString2(Date date){
		return ymdhmsFormat2.format(date);
	}
	
	public static String getYmdHmsString(Date date){
		return ymdhmsFormat.format(date);
	}
	
	/**
	 * 获得今天年月y日字符串：20180329
	 * @return
	 */
	public static String getTodayYMDString(){
		return getYMDString(new Date());
	}
	
	/**
	 * 获得此刻
	 * @return
	 */
	public static String getNowYmdHmsString(){
		return getYmdHmsString(new Date());
	}
	

}
