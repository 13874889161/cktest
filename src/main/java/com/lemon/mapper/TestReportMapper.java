package com.lemon.mapper;

import com.lemon.common.CasesListVO;
import com.lemon.common.ReportVO;
import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.TestReport;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
public interface TestReportMapper extends BaseMapper<TestReport> {

	/**
	 * 查询指定案例的最新一次的测试结果
	 * @param caseId
	 * @return
	 */
	@Select("SELECT * FROM test_report WHERE id=(SELECT max(id) FROM test_report WHERE case_id=#{caseId})")
	TestReport findReportByCase(Integer caseId);
	
	/**
	 * 通过报告id查询测试报告
	 * @param reptId
	 * @return
	 */
	@Select("select *  from  test_report where rept_id=#{reptId}")
	List<TestReport> findByTestReport(Integer reptId);
	
	
	/**
	 * 通过套件id和报告id分组查询测试报告
	 * @param reptId
	 * @param suiteId
	 * @return
	 */
	@Select("SELECT t1.*,t2.`name` caseName FROM test_report t1 INNER JOIN cases t2 ON t1.case_id=t2.id LEFT JOIN suite t3 ON t2.suite_id=t3.id WHERE t1.rept_id=#{reptId} AND t3.id=#{suiteId}")
	List<TestReport> findTestReportByReptIdAndSuiteId(Integer reptId,Integer suiteId);
}
