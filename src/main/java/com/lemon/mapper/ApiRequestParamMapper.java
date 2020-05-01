package com.lemon.mapper;

import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.CaseParamValue;

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
public interface ApiRequestParamMapper extends BaseMapper<ApiRequestParam> {
	/**
	 * 通过apiId查询参数
	 * @param apiId
	 * @return
	 */
	@Select("select  *  from api_request_param  where api_id=#{apiId}")
	public List<ApiRequestParam> findApiParam(Integer apiId);
	
	/**
	 * 通过apiId查询参数
	 * @param apiId
	 * @return
	 */
	@Select("SELECT t2.*,t1.api_request_param_value VALUE,t1.id valueId FROM case_param_value t1 RIGHT JOIN api_request_param t2 ON t1.api_request_param_id=t2.id AND t1.case_id=#{caseId} WHERE t2.api_id=#{apiId}")
	public List<ApiRequestParam> findByCase(Integer caseId,Integer apiId);
	/**
	 * 通过caseid查询参数
	 * @param caseId
	 * @return
	 */
	@Select("SELECT t2.*,t1.api_request_param_value value,t1.id valueId FROM case_param_value t1 left JOIN api_request_param t2 ON t1.api_request_param_id=t2.id WHERE t1.case_id=#{caseId}")
	public List<ApiRequestParam> findCaseByCaseId(Integer caseId);
}
