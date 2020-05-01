package com.lemon.common;

import java.util.ArrayList;
import java.util.List;

import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.CaseParamValue;
import com.lemon.pojo.Cases;
import com.lemon.pojo.TestRule;

import lombok.Data;

@Data
public class CaseEditVO  extends Cases{
    //private Integer id; //caseId
	//private String name;//caseName
	private String method; //请求方法
	private String url;//请求url
	//private Integer apiId;//接口id
	private String host;//项目host
	private String projectName;//项目名称
	private String suiteName;//套件名称
	
	private List<ApiRequestParam> requestParams=new ArrayList<ApiRequestParam>();//参数信息
	//private List<CaseParamValue> caserequestParams=new ArrayList<CaseParamValue>();//参数信息
	private List<TestRule> testRules=new ArrayList<TestRule>();//断言信息
	
	
}
