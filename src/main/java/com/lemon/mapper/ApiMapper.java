package com.lemon.mapper;

import com.lemon.common.ApiListVO;
import com.lemon.common.ApiVO;
import com.lemon.pojo.Api;

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
public interface ApiMapper extends BaseMapper<Api> {
	
	/**
	 * 通过分类id查询对应的接口信息
	 * @param apiClassificationId
	 * @return
	 */
	@Select("select  *  from api where api_classification_id=#{apiClassificationId}")
	public List<Api> findApi(Integer apiClassificationId);
	
	/**
	 * 查询项目下所有的接口列表
	 * @param projectId
	 * @return
	 */
	@Select("select t2.`name` classificationName,t1.*  from api t1 inner join api_classification t2 on t1.api_classification_id=t2.id where t2.project_id=#{projectId}"  )
	public List<ApiListVO>  showApiListByProject(Integer projectId);
	
	/**
	 * 查询指定分类下接口
	 * @param classificationId
	 * @return
	 */
	@Select("select t2.`name` classificationName,t2.`description` classificationDescription,t1.*  from api t1 inner join api_classification t2 on t1.api_classification_id=t2.id where t2.id=#{classificationId}" )
	public List<ApiListVO>  showApiListByclassificationId(Integer classificationId);
	
	/**
	 * 查询指定接口对应信息
	 * @param apiId
	 * @return
	 */
	@Select("select   t1.*,t2.username createUserName from api t1 inner join user t2 on t1.create_user=t2.id where t1.id=#{apiId}")
	@Results({
		    @Result(column="id", property="id"), //如果查询的结果列名与实体类名称一致；可以省略
		    @Result(column="id", property="requestParams",many=@Many(select="com.lemon.mapper.ApiRequestParamMapper.findApiParam"))
	})
	public ApiVO findApiViewVO(Integer apiId);
 	
	
}
