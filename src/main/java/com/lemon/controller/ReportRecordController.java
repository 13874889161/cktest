package com.lemon.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.lemon.common.PageRequest;
import com.lemon.common.PageResult;
import com.lemon.common.ReportVO;
import com.lemon.common.Result;
import com.lemon.common.TestReportRequest;
import com.lemon.pojo.TestReport;
import com.lemon.service.ReportRecordService;

import io.swagger.annotations.Api;
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
@RequestMapping("/reportRecord")
@Api("报告记录模块")
@CrossOrigin 
public class ReportRecordController {
	@Autowired
	ReportRecordService reportRecordService;
	
	//没有翻页；没有动态sql的功能
	@PostMapping("/getReportList")
	@ApiOperation(value="查询测试报告列表",httpMethod="POST")
	public Result getReportList(Integer projectId){
		List<ReportVO> reportVOList=reportRecordService.getReportList(projectId);
		return new Result("1",reportVOList,"1");
	}
	//只有翻页，没有动态sql的功能
	@PostMapping("/getReportListPage")
	@ApiOperation(value="查询测试报告列表",httpMethod="POST")
	public Result getReportListPage(PageRequest pageRequest,Integer projectId){
		PageResult pageResult=reportRecordService.getReportListtest(pageRequest, projectId);
		return new Result("1",pageResult,"1");
	}
	/**
	 * 动态sql查询测试报告列表；并进行分页处理
	 * @param pageRequest
	 * @param testReportRequest
	 * @return
	 */
	@PostMapping("/getReportListPage2")
	@ApiOperation(value="查询测试报告列表",httpMethod="POST")
	public Result getReportListPage2(PageRequest pageRequest,TestReportRequest testReportRequest){
		PageResult pageResult=reportRecordService.getReportList2(pageRequest, testReportRequest);
		return new Result("1",pageResult,"1");
	}
	
	/**
	 * 查询指定报告记录
	 * @param reptId
	 * @return
	 */
	@PostMapping("/getReportListByReptId")
	@ApiOperation(value="查询指定报告记录",httpMethod="POST")
	public Result getReportListByReptId(Integer reptId){
		List<ReportVO> reportVOList =reportRecordService.getReportListByReptId(reptId);
		return new Result("1",reportVOList,"1");
	}
}
