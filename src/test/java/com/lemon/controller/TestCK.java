package com.lemon.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.UnsupportedEncodingException;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alibaba.fastjson.JSONPath;


@SpringBootTest   //支持单元测试
@RunWith(SpringRunner.class)  //使用SpringRunner进行单元测试  底层就是junit
//@RunWith(DataProv) //DataProviderRunner.class
@AutoConfigureMockMvc //加入mockMVC存放到ioc容器中--伪造前端
public class TestCK {
	@Autowired
	private MockMvc mockMvc;//从ioc容器中获取mockMvc对象
	String sessionId;
	
	
  
	
	@Before
	@Test
	public void testlogin() throws UnsupportedEncodingException, Exception{
		//perform：方法--建造器；伪造一个请求
		String resultJson=mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
		.param("username","001@163.com")
		.param("password", "96e79218965eb72c92a549dd5a330112")
		)
		.andDo(print())  //打印测试结果
		.andExpect(status().isOk())  //断言响应状态码为200
		.andReturn().getResponse().getContentAsString();//返回值(响应体内容)
		sessionId=(String) JSONPath.read(resultJson, "$.message");
	}
	
	//@Ignore 忽略
	@Test
	public void test() throws UnsupportedEncodingException, Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/user/find")
				.header("Authorization", sessionId)
				.param("username","001@163.com")
				)
				.andDo(print())  //打印测试结果
				.andExpect(status().isOk())  //断言响应状态码为200
				.andExpect(jsonPathEquals("$.message","账号已存在"))
				.andExpect(jsonPathEquals("$.status","0"))
				.andReturn().getResponse().getContentAsString();//返回值(响应体内容)
	}
	/**
	 * jsonpath验证   等于
	 * @param matcher  正则表达式
	 * @param expected  预期结果
	 * @return
	 */
	public ResultMatcher jsonPathEquals(String matcher,String expected){
		return MockMvcResultMatchers.jsonPath(matcher, Matchers.is(expected));
	}
	
	
}
