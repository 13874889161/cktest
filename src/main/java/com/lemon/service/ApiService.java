package com.lemon.service;

import com.lemon.common.ApiListVO;
import com.lemon.common.ApiRunResult;
import com.lemon.common.ApiVO;
import com.lemon.common.Result;
import com.lemon.pojo.Api;

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
public interface ApiService extends IService<Api> {
	//查询项目下抽象方法
	public List<ApiListVO>  showApiListByProject(Integer projectId);
	
	//查询指定分类抽象方法
	public List<ApiListVO>  showApiListByclassificationId(Integer classificationId);

	//查询指定api信息
	public ApiVO findApiViewVO(Integer apiId);
	
	public Result edit(ApiVO apiVo);
	/**
	 * 运行接口的抽象方法
	 * @param apiVO
	 * @return
	 */
	public ApiRunResult run(ApiVO apiVO);
}
