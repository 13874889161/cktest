package com.lemon.service.impl;

import com.lemon.pojo.ApiClassification;
import com.lemon.common.ApiClassificationVO;
import com.lemon.mapper.ApiClassificationMapper;
import com.lemon.service.ApiClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
@Service
public class ApiClassificationServiceImpl extends ServiceImpl<ApiClassificationMapper, ApiClassification> implements ApiClassificationService {
	
	@Autowired
	ApiClassificationMapper apiClassificationMapper;//dao层对象
	/**
	 * 服务层调用dao层操作数据方法
	 */
	@Override
	public List<ApiClassificationVO> getWithApi(Integer projectId) {
		return apiClassificationMapper.getWithApi(projectId);
	}

}
