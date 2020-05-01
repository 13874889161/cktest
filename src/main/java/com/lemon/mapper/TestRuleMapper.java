package com.lemon.mapper;

import com.lemon.pojo.TestReport;
import com.lemon.pojo.TestRule;

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
public interface TestRuleMapper extends BaseMapper<TestRule> {

	
	/**
	 * 通过caseId断言参数
	 * @param caseId
	 * @return
	 */
	@Select("SELECT t1.*,t2.rule_type_name ruleName,t3.rule_type_name operatorName FROM test_rule t1 LEFT JOIN rule_type t2 ON t1.rule=t2.rule_type_id LEFT JOIN rule_type t3 ON t1.operator=t3.rule_type_id WHERE case_id=#{caseId}")
	public List<TestRule> findByCase(Integer caseId);
}
