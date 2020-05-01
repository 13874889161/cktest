package com.lemon.mapper;

import com.lemon.common.ReportVO;
import com.lemon.common.TestReportRequest;
import com.lemon.pojo.ReportRecord;
import com.lemon.utils.SqlProviderUtil;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
public interface ReportRecordMapper extends BaseMapper<ReportRecord> {

	@Select("select t1.*,t2.username from report_record t1 left join user t2 on  t1.create_user=t2.id where t1.project_id=#{projectId} ORDER BY create_time desc")
	@Results({
	    @Result(column="rept_id", property="reptId"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="rept_id", property="testReports",many=@Many(select="com.lemon.mapper.TestReportMapper.findByTestReport")),
	})
	public List<ReportVO> getReportList(Integer projectId);
	
	/**
	 * 组合搜索和分页
	 * @param testReportRequest
	 * @return
	 */
	@SelectProvider(type=SqlProviderUtil.class,method="getReportList2")
	@Results({
	    @Result(column="rept_id", property="reptId"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="rept_id", property="testReports",many=@Many(select="com.lemon.mapper.TestReportMapper.findByTestReport")),
	})
	public List<ReportVO> getReportList2(TestReportRequest testReportRequest);
	
	/**
	 * 以套件id
	 * @param reptId
	 * @return
	 */
	@Select("SELECT DISTINCT t1.*,t1.name suiteName,t4.rept_id ,t4.rept_name reptName,t5.username  FROM suite t1 INNER JOIN cases t2 ON t1.id=t2.suite_id LEFT JOIN test_report t3 ON t2.id=t3.case_id LEFT JOIN report_record t4 ON t3.rept_id=t4.rept_id left join user t5 on t4.create_user=t5.id WHERE t4.rept_id=#{reptId}")
	@Results({
	    @Result(column="rept_id", property="reptId"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="{reptId=rept_Id,suiteId=id}", property="testReports",many=@Many(select="com.lemon.mapper.TestReportMapper.findTestReportByReptIdAndSuiteId")),
	})
	public List<ReportVO> getReportListByReptId(Integer reptId);
}
