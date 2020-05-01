package com.lemon.common;

import lombok.Data;

@Data
public class ApiRunResult {

	private String statusCode;//响应状态码
	private String headers;//响应头部信息
	private String body;//响应体
	
}
