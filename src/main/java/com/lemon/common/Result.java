package com.lemon.common;

import lombok.Data;

@Data
public class Result {
	private String  status; //返回状态码值  0-请求失败   1-请求成功       
	private Object data;//返回的数据
	private String message;//响应信息
	public Result(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public Result(String status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}
	public Result(String status, Object data, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
	}
		
}
