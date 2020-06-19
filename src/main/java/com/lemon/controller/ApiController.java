package com.lemon.controller;


import java.util.List;

import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.ApiListVO;
import com.lemon.common.ApiRunResult;
import com.lemon.common.ApiVO;
import com.lemon.common.Result;

import com.lemon.pojo.ApiClassification;
import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.User;
import com.lemon.service.ApiRequestParamService;
import com.lemon.service.ApiService;
import com.lemon.pojo.Api;
//import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/api")
@io.swagger.annotations.Api("接口模块")
@CrossOrigin 
public class ApiController {
	
	@Autowired
	ApiService apiService;
	
	@Autowired
	ApiRequestParamService apiRequestParamService;
	/**
	 *1、 通过项目id查询接口
	 * @param apiClassificationId
	 * @return
	 */
	@GetMapping("/showApiListByProject")
	@ApiOperation(value="查询项目下所有接口信息",httpMethod="GET")
	public  Result showApiListByProject(Integer projectId){
		List<ApiListVO> apiList=  apiService.showApiListByProject(projectId);
		return new Result("1",apiList );
	}
	
	/**
	 *2、通过分类id查询接口
	 * @param apiClassificationId
	 * @return
	 */
	@GetMapping("/showApiListByClassificationId")
	@ApiOperation(value="查询分类下所有接口信息",httpMethod="GET")
	public  Result showApiListByclassificationId(Integer apiClassificationId){
		List<ApiListVO> apiList=  apiService.showApiListByclassificationId(apiClassificationId);
		return new Result("1",apiList );
		
	}
	/**
	 *3、 新增接口的api
	 * @param api 前端返回的接口对象
	 * @return
	 */
	@PostMapping("/addApi")
	@ApiOperation(value="新增接口",httpMethod="POST")
	public Result addApi( Api api){
		User user=(User) SecurityUtils.getSubject().getPrincipal();
		api.setCreateUser(user.getId());
		apiService.save(api);
		return new Result("1", "新增成功");
	}
	/**
	 * 查询指定接口基本信息
	 * @param apiId  接口id
	 * @return
	 */
	@PostMapping("/findApi")
	@ApiOperation(value="查询指定接口基本信息",httpMethod="POST")
	public Result findApi(Integer apiId){
		System.out.println(apiId);
		ApiVO apiVO= apiService.findApiViewVO(apiId);
		return new Result("1", apiVO);
	}
	
	
	/**
	 * 更新保存接口信息
	 * @param apiId  接口id
	 * @return
	 */
	@PostMapping("/edit")
	@ApiOperation(value="更新接口",httpMethod="POST")
	public Result edit(ApiVO apiVo){
		return apiService.edit(apiVo);
	}
	/**
	 * 远程调用，运行接口
	 * @param apiVO
	 * @return
	 */
	@RequestMapping("/apiRun")
	public  Result apiRun(ApiVO apiVO){
		ApiRunResult apiRunResult=apiService.run(apiVO);
		return new Result("1",apiRunResult, "执行成功");
	}
}
