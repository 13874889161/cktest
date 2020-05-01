package com.lemon.common;

import java.util.ArrayList;
import java.util.List;

import com.lemon.pojo.Api;
import com.lemon.pojo.ApiRequestParam;

import lombok.Data;

@Data
public class ApiVO  extends Api{
	//用户名称
	private String createUserName;
	
	//地址
	private String host;
	
	//所有参数集合
	private List<ApiRequestParam> requestParams=new ArrayList<ApiRequestParam>();
	
	///////后续运行时使用
	//参数1
	private List<ApiRequestParam> queryParams=new ArrayList<ApiRequestParam>();
	
	//参数2
	private List<ApiRequestParam> bodyParams=new ArrayList<ApiRequestParam>();
	
	//参数3
	private List<ApiRequestParam> headerParams=new ArrayList<ApiRequestParam>();
	
	//参数4
	private List<ApiRequestParam> bodyRawParams=new ArrayList<ApiRequestParam>();
	
	
}
