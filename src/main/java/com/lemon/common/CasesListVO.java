package com.lemon.common;

import com.lemon.pojo.TestReport;

import lombok.Data;

@Data
public class CasesListVO {
	private String id; //caseid
	private String name; //casename
//	private String suiteName; //集合名称
//	private String suiteDescription;//集合描述
	private String apiId;//接口id
	private String apiUrl;//接口地址
	
	private TestReport testReport; //测试报告
}
