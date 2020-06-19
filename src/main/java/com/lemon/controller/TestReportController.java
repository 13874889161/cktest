package com.lemon.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.lemon.common.Result;
import com.lemon.pojo.TestReport;
import com.lemon.service.TestReportService;

import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/testReport")
@CrossOrigin 
public class TestReportController {
	@Autowired
	TestReportService testReportService;
	
	@PostMapping("/runTest")
	@ApiOperation(value="执行测试案例",httpMethod="POST")
	public Result runTest(Integer projectId,Integer suiteId){
		List<TestReport> reportList=testReportService.runTest( projectId, suiteId);
		return new Result("1",reportList, "运行成功");
	}
	
	/**
	 * 获取最新的测试结果
	 * @param projectId
	 * @param suiteId
	 * @return
	 */
	@PostMapping("/findCaseRunResult")
	@ApiOperation(value="获取单个案例测试结果",httpMethod="POST")
	public Result findCaseRunResult(Integer caseId){
		TestReport testReport=testReportService.findCaseRunResult(caseId);
		return new Result("1",testReport,"1");
	}
	
	
}
