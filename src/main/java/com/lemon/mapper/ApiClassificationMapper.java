package com.lemon.mapper;

import com.lemon.common.ApiClassificationVO;
import com.lemon.pojo.ApiClassification;

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
public interface ApiClassificationMapper extends BaseMapper<ApiClassification> {

	
	/**
	 * 通过项目id查询分类结果--再通过分类id查询接口列表
	 * @param projectId
	 * @return
	 */
	@Select("select  *  from  api_classification where project_id =#{projectId}") //执行查询语句1
	@Results({ //查询返回结果
		//column数据库查询返回的列名；property:实体类(pojo)属性名
	    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
	    @Result(column="name", property="name"),
	    @Result(column="project_id", property="projectId"),
	    //使用语句1中查询结果的id列的值，作为条件传给ApiMapper类中的findApi方法；得到的结果赋值给apis
	    @Result(column="id", property="apis",many=@Many(select="com.lemon.mapper.ApiMapper.findApi"))
	})
	public List<ApiClassificationVO> getWithApi(Integer projectId);
}
