package com.lemon.handler;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName CorsFilterConfiguration
 * @Description 跨域资源共享
 * @Author MatthewHan
 * @Date 2019/10/15 10:48
 * @Version 1.0
 **/
@WebFilter(filterName = "CorsFilterConfig ")
@Order(-100)
@Component
@ServletComponentScan
public class CorsFilterConfig implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(CorsFilterConfig.class);

	private static String[] allowDomains = { "http://localhost:8080", "http://localhost:80", "http://ip:8080",
			"http://ip:80", "http://ip:8080", "http://ip:80" };

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST,GET,PATCH,DELETE,PUT,OPTIONS");
		/*
		 * 该字段可选，用来指定本次预检请求的有效期，单位为秒。上面结果中，有效期是1小时（3600秒），
		 * 即允许缓存该条回应3600秒（即1小时），在此期间，不用发出另一条预检请求。
		 */
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		// prefight请求
		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(200);
			return;
		}
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {

	}

	// @Override
	// public void doFilter1(ServletRequest arg0, ServletResponse arg1,
	// FilterChain arg2)
	// throws IOException, ServletException {
	// // TODO Auto-generated method stub
	//
	// }
}
