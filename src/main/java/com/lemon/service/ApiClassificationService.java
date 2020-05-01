package com.lemon.service;

import com.lemon.common.ApiClassificationVO;
import com.lemon.pojo.ApiClassification;

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
public interface ApiClassificationService extends IService<ApiClassification> {

	
	/**
	 * 查询接口和接口分类的抽象方法
	 * @param projectId  项目id
	 * @return
	 */
	public List<ApiClassificationVO> getWithApi(Integer projectId);
}
