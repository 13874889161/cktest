package com.lemon.service;

import com.lemon.common.PageRequest;
import com.lemon.common.PageResult;
import com.lemon.common.ReportVO;
import com.lemon.common.TestReportRequest;
import com.lemon.pojo.ReportRecord;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
public interface ReportRecordService extends IService<ReportRecord> {

	
	/**
	 * 查询项目下测试报告列表
	 * @param projectId
	 * @return
	 */
	List<ReportVO> getReportList(Integer projectId);
	/**
	 * 分页查询
	 * @param pageRequest
	 * @param projectId
	 * @return
	 */
	PageResult getReportListtest(PageRequest pageRequest,Integer projectId);
	
	/**
	 * 动态sql
	 * @param pageRequest
	 * @param testReportRequest
	 * @return
	 */
	PageResult getReportList2(PageRequest pageRequest,TestReportRequest testReportRequest);
	
	List<ReportVO> getReportListByReptId(Integer reptId);
}
