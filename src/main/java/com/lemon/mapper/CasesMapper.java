package com.lemon.mapper;

import com.lemon.common.CaseEditVO;
import com.lemon.common.CasesListVO;
import com.lemon.pojo.Cases;
import com.lemon.pojo.Suite;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
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
public interface CasesMapper extends BaseMapper<Cases> {
	
	/**
	 * 通过suiteId查询所有的案例
	 * @param suiteId
	 * @return
	 */
	@Select("select *  from cases where suite_id=#{suiteId}")
	List<Cases> findCases(Integer suiteId);
	
	/**
	 * 查询项目下所有案例/拼接接口信息
	 * @param projectId
	 * @return
	 */
	@Select("SELECT t1.*,t3.url apiUrl FROM cases t1 LEFT JOIN suite t2 ON t1.suite_id=t2.id LEFT JOIN api t3 ON t1.api_id=t3.id WHERE t2.project_id=#{projectId}")
	@Results({
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="id", property="testReport",one=@One(select="com.lemon.mapper.TestReportMapper.findReportByCase"))

	})
	List<CasesListVO> showCaseUnderProject(Integer projectId);
	

	/**
	 * 查询suite下的案例/拼接接口信息
	 * @param suiteId
	 * @return
	 */
	@Select("SELECT t1.*,t3.url apiUrl  FROM cases t1 LEFT JOIN suite t2 ON t1.suite_id=t2.id LEFT JOIN api t3 ON t1.api_id=t3.id WHERE t2.id=#{suiteId}")
	@Results({
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="id", property="testReport",one=@One(select="com.lemon.mapper.TestReportMapper.findReportByCase"))

	})
	List<CasesListVO> showCaseUnderSuiteId(Integer suiteId);
	
	/**
	 * 查询指定case的信息-携带参数CaseEditVO对象
	 * @param caseId
	 * @return
	 */
	@Select("SELECT t1.*,t2.url,t2.method,t4.`host` FROM cases t1 LEFT JOIN api t2 ON t1.api_id=t2.id LEFT JOIN suite t3 ON t1.suite_id=t3.id LEFT JOIN project t4 ON t3.project_id=t4.id WHERE t1.id=#{caseId}")
	@Results({
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="suite_id", property="suiteId"),
	    @Result(column="api_id", property="apiId"),
	    @Result(column="{caseId=id,apiId=api_id}", property="requestParams",many=@Many(select="com.lemon.mapper.ApiRequestParamMapper.findByCase")),
	    @Result(column="id", property="testRules",many=@Many(select="com.lemon.mapper.TestRuleMapper.findByCase"))

	})
	CaseEditVO findCaseEditVO(Integer caseId);
	
	/**
	 * 查询项目下所有测试案例--用来执行
	 * @param projectId
	 * @return
	 */
	@Select("SELECT t4.NAME projectName,t4.`host`,t2.name suiteName,t3.url,t3.method,t1.*  FROM cases t1 LEFT JOIN suite t2 ON t1.suite_id=t2.id LEFT JOIN api t3 ON t1.api_id=t3.id LEFT JOIN project t4 ON t2.project_id=t4.id WHERE t2.project_id=#{projectId} ORDER BY t1.suite_id,t1.id ASC")
	@Results({
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="suite_id", property="suiteId"),
	    @Result(column="api_id", property="apiId"),
	    @Result(column="id", property="requestParams",many=@Many(select="com.lemon.mapper.ApiRequestParamMapper.findCaseByCaseId")),
	    @Result(column="id", property="testRules",many=@Many(select="com.lemon.mapper.TestRuleMapper.findByCase"))
	})
	List<CaseEditVO> findCaseByPorject(Integer projectId);
	
	/**
	 * 查询套件下所有测试案例--用来执行
	 * @param suiteId
	 * @return
	 */
	@Select("SELECT t4.NAME projectName,t4.`host`,t2.name suiteName,t3.url,t3.method,t1.* FROM cases t1 LEFT JOIN suite t2 ON t1.suite_id=t2.id LEFT JOIN api t3 ON t1.api_id=t3.id LEFT JOIN project t4 ON t2.project_id=t4.id WHERE t2.id=#{suiteId} ORDER BY t1.id ASC")
	@Results({
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="suite_id", property="suiteId"),
	    @Result(column="api_id", property="apiId"),
	    @Result(column="id", property="requestParams",many=@Many(select="com.lemon.mapper.ApiRequestParamMapper.findCaseByCaseId")),
	    @Result(column="id", property="testRules",many=@Many(select="com.lemon.mapper.TestRuleMapper.findByCase"))
	})
	List<CaseEditVO> findCaseBySuite(Integer suiteId);
}
