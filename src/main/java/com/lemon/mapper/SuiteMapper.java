package com.lemon.mapper;

import com.lemon.pojo.Suite;

import java.util.List;

import org.apache.ibatis.annotations.Many;
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
public interface SuiteMapper extends BaseMapper<Suite> {

	@Select("select *  from suite where project_id=#{projectId}")
	@Results({
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略com.lemon.mapper.CasesMapper
	    @Result(column="id", property="cases",many=@Many(select="com.lemon.mapper.CasesMapper.findCases"))
	})
	List<Suite> findSuiteAndReleadtedCasesBy(Integer projectId);
}
