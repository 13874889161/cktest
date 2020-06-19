package com.lemon.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.lemon.common.Result;
import com.lemon.pojo.RuleType;
import com.lemon.service.RuleTypeService;

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
@RequestMapping("/ruleType")
@Api("断言类型模块")
@CrossOrigin 
public class RuleTypeController {
	@Autowired
	RuleTypeService  ruleTypeService;
	
	@PostMapping("/findRuleType")
	@ApiOperation(value="查询断言类型",httpMethod="POST")
	public Result findRuleType(){
		List<RuleType> ruletypelist=ruleTypeService.list();
		return new Result("1", ruletypelist);
	}
}
