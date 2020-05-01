package com.lemon.utils;

import java.util.List;

import com.alibaba.fastjson.JSONPath;

public class AssertRuleUtil {

	/**
	 * 包含断言
	 * @param actualted  实际结果
	 * @param expected   预期结果
	 * @return
	 */
	public static boolean assertByContains(String actualted,String expected ){
		return actualted.contains(expected);
	}
	
	/**
	 * 不包含断言
	 * @param actualted  实际结果
	 * @param expected   预期结果
	 * @return
	 */
	public static boolean assertByNotContains(String actualted,String expected ){
		return !actualted.contains(expected);
	}
	/**
	 * 等于
	 * @param actualted  实际结果
	 * @param expected   预期结果
	 * @return
	 */
	public static boolean assertByEquals(String actualted,String expected ){
		return actualted.equals(expected);
	}
	
	/**
	 * 不等于等于
	 * @param actualted  实际结果
	 * @param expected   预期结果
	 * @return
	 */
	public static boolean assertByNotEquals(String actualted,String expected ){
		return !actualted.equals(expected);
	}
	
	/**
	 * 使用jsonPath断言值是否正确
	 * @param jsonStr  实际结果 json字符串
	 * @param expression 校验规则
	 * @param expected 预期结果
	 * @return
	 */
	public static boolean assertByJsonPathValue(String jsonStr,String expression,String operator,String expected ){
		String value=(String) JSONPath.read(jsonStr, expression);
		boolean testResult=false;
		if(operator.equals("=")){
			testResult=value.equals(expected);
		}else if(operator.equals("contains")){
			testResult=value.contains(expected);
		}else if(operator.equals("notcontains")){
			testResult=!value.contains(expected);
		}else if(operator.equals("!=")){
			testResult=!value.equals(expected);
		}
		return testResult;
	}
	
	/**
	 * 使用jsonPath断言数量是否正确
	 * @param jsonStr  实际结果 json字符串
	 * @param expression 校验规则
	 * @param expected 预期结果
	 * @return
	 */
	public static boolean assertByJsonPathCount(String jsonStr,String expression,String expected ){
		//String value=(String) JSONPath.read(jsonStr, expression);
		//不知道是否有用
		List<String> valueList=(List<String>) JSONPath.read(jsonStr, expression);
		String count=valueList.size()+"";
		return count.equals(expected);
	}
	
	/**
	 * 脚本断言//待开发
	 * @return
	 */
	public static boolean assertByScript(String actualted,String expected ){
		return true;
	}
}
