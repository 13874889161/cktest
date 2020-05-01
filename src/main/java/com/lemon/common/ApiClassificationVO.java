package com.lemon.common;

import java.util.List;

import com.lemon.pojo.Api;
import com.lemon.pojo.ApiClassification;

import lombok.Data;

@Data
public class ApiClassificationVO extends ApiClassification{

	private List<Api> apis; //接口信息
	
}
