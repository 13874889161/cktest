package com.lemon.controller;


import java.util.List;

import javax.management.Query;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.Result;
import com.lemon.pojo.Suite;
import com.lemon.pojo.User;
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
@RequestMapping("/suite")
@Api("测试套件模块")
@CrossOrigin 
public class SuiteController {
	@Autowired
	SuiteService suiteService;
	
	/**
	 * 新增测试集合
	 * @param suite
	 * @return
	 */
	@PostMapping("/add")
	@ApiOperation(value="新增测试集合",httpMethod="POST")
	public Result add(Suite suite){
		Subject subject=SecurityUtils.getSubject();
		User  user=(User) subject.getPrincipal();
		suite.setCreateUser(user.getId());
		suiteService.save(suite);
		return new Result("1", "新增成功");
	}
	
	
	/**
	 * 查询项目下所有集合
	 * @param projectId
	 * @return
	 */
	@PostMapping("/listAll")
	@ApiOperation(value="查询项目的测试集合",httpMethod="POST")
	public Result findAll(Integer projectId){
		QueryWrapper queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("project_id", projectId);
		List<Suite> suites=suiteService.list(queryWrapper);
		return new Result("1",suites);
	}
	/**
	 * 删除集合
	 * 此处仅仅删除集合表；其他关联表后续再进行操作
	 * @param suiteId
	 * @return
	 */
	@PostMapping("/deleteSuite")
	@ApiOperation(value="删除测试集合",httpMethod="POST")
	public Result deleteSuite(Integer suiteId){
		
		suiteService.removeById(suiteId);
		return new Result("1", "删除成功");
	}
	/**
	 * 通过id查询集合信息
	 * @param suiteId
	 * @return
	 */
	@GetMapping("/getSuiteById")
	@ApiOperation(value="通过id查询集合信息",httpMethod="GET")
	public  Result getSuiteById(Integer suiteId){
		Suite suite=suiteService.getById(suiteId);
		return new Result("1", suite);
	}
	/**
	 * 保存集合
	 * @param suite
	 * @return
	 */
	@PostMapping("/saveSuite")
	@ApiOperation(value="保存案例集合",httpMethod="POST")
	public Result saveSuite(Suite suite){
		suiteService.updateById(suite);
		return new Result("1","保存成功");
	}
}
