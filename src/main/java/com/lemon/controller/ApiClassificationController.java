package com.lemon.controller;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.ApiClassificationVO;
import com.lemon.common.Result;
import com.lemon.pojo.ApiClassification;
import com.lemon.pojo.Suite;
import com.lemon.pojo.User;
import com.lemon.service.ApiClassificationService;
import com.lemon.service.SuiteService;

import io.swagger.annotations.Api;
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
@RequestMapping("/apiClassification")
@Api("接口分类模块")
@CrossOrigin 
public class ApiClassificationController {
	@Autowired
	ApiClassificationService apiClassificationService;
	
	@Autowired
	SuiteService suiteService;
	/**
	 * 新增接口分类的方法
	 * @param projectId  项目id
	 * @param apiClassification 前端传入接口分类对象数据
	 * @return
	 */
	@PostMapping("/{projectId}")
	@ApiOperation(value="新增接口分类方法",httpMethod="POST")
	public  Result addApiClassification(@PathVariable("projectId") Integer projectId,ApiClassification apiClassification){
		User  user=(User) SecurityUtils.getSubject().getPrincipal();
		apiClassification.setProjectId(projectId);
		apiClassification.setCreateUser(user.getId());
		apiClassificationService.save(apiClassification);
		return new Result("1", "新增成功");
	}
	/**
	 * 查询接口分类和接口列表方法
	 * @param projectId  项目id
	 * @param tab   查询类型 1-接口列表   2-测试集合
	 * @return
	 */
	@GetMapping("/toIndex")
	@ApiOperation(value="查询接口分类和接口列表方法",httpMethod="GET")
	public Result getWithApi(Integer projectId,String  tab){
		Result result=null;
		if(tab.equals("1")){
			//接口列表
			List<ApiClassificationVO> apiClassificationVOlist=  apiClassificationService.getWithApi(projectId);
			result=new Result("1", apiClassificationVOlist, "查询接口分类和接口");
		}else if("2".equals(tab)){
			//测试集合
			List<Suite> suites=suiteService.findSuiteAndReleadtedCasesBy(projectId);
			result=new Result("1", suites, "查询集合和案例");
		}
		return result;
	}
	/**
	 * 删除接口分类的方法
	 * @param apiClassificationId
	 * @return
	 */
	@GetMapping("/deleteApiClassification")
	@ApiOperation(value="删除接口分类的方法",httpMethod="GET")
	public Result deleteApiClassification(Integer apiClassificationId){
		apiClassificationService.removeById(apiClassificationId);
		return new Result("1","删除成功");
	}
	/**
	 * 查询分类信息
	 * @param apiClassificationId
	 * @return
	 */
	@GetMapping("/{apiClassificationId}")
	@ApiOperation(value="查询分类信息",httpMethod="GET")
	public  Result getApiClassificationById(@PathVariable("apiClassificationId") Integer apiClassificationId){
		ApiClassification apiClassification=apiClassificationService.getById(apiClassificationId);
		return new Result("1", apiClassification);
	}
	
	/**
	 * 更新分类信息
	 * @param apiClassification  分类对象
	 * @return
	 */
	@PostMapping("/updateApiClassification")
	@ApiOperation(value="更新分类信息",httpMethod="GET")
	public  Result updateApiClassification(ApiClassification apiClassification){
		//apiClassification.setId(apiClassificationId);
		apiClassificationService.updateById(apiClassification);
		//ApiClassification apiClassification=apiClassificationService.getById(apiClassificationId);
		return new Result("1", "更新成功");
		
	}
	/**
	 * 查询所有分类信息方法
	 * @param projectId
	 * @return
	 */
	@PostMapping("/findAll")
	@ApiOperation(value="查询所有分类信息",httpMethod="POST")
	public Result findAll(Integer projectId){
		QueryWrapper queryWrapper=new QueryWrapper();
		queryWrapper.eq("project_id", projectId);
		List<ApiClassification> list=apiClassificationService.list(queryWrapper);
		return new Result("1", list);
	}
	/**
	 * 新增分类的方法  使用json字符串传参格式
	 * @param jsonStr
	 * @return
	 */
	@PostMapping("/add2")
	@ApiOperation(value="新增分类方法2json格式",httpMethod="POST")
	public Result add2(@RequestBody String jsonStr){
		//将json字符串转成java对象
		System.out.println(jsonStr);
		ApiClassification apiClassification=JSON.parseObject(jsonStr, ApiClassification.class);
		apiClassificationService.save(apiClassification);
		return new  Result("1","新增分类成功");
		
	}
	
}
