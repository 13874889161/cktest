package com.lemon.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lemon.common.ApiVO;
import com.lemon.common.CaseEditVO;
import com.lemon.common.CasesListVO;
import com.lemon.common.Result;
import com.lemon.pojo.Cases;
import com.lemon.service.CaseParamValueService;
import com.lemon.service.CasesService;

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
@RequestMapping("/cases")
@Api("测试案例模块")
@CrossOrigin 
public class CasesController {

	@Autowired
	CasesService casesService ;
	@Autowired
	CaseParamValueService caseParamValueService ;
	
	/**
	 * 添加测试案例到集合中
	 * @param cases
	 * @param apiVO
	 * @return
	 */
	@PostMapping("/add")
	@ApiOperation(value="添加案例",httpMethod="POST")
	public Result add(Cases cases,ApiVO apiVO){
		return  casesService.add(cases, apiVO);
	}
	/**
	 * 通过项目id查询所有测试案例
	 * @param projectId
	 * @return
	 */
	@PostMapping("/findCasesByProjectId")
	@ApiOperation(value="查询项目下所有测试案例",httpMethod="POST")
	public Result findCasesByProjectId(Integer projectId){
		List<CasesListVO> casesListVOs=casesService.showCaseUnderProject(projectId);
		return new Result("1", casesListVOs,"1");
	}
	
	/**
	 * 通过集合id查询集合下测试案例
	 * @param projectId
	 * @return
	 */
	@PostMapping("/findCasesBySuiteId")
	@ApiOperation(value="查询集合下测试案例",httpMethod="POST")
	public Result findCasesBySuiteId(Integer suiteId){
		List<CasesListVO> casesListVOs=casesService.showCaseUnderSuiteId(suiteId);
		return new Result("1", casesListVOs,"1");
	}
	/**
	 * 通过caseId查询案例信息
	 * @param caseId
	 * @return
	 */
	@PostMapping("/findCaseEditVO")
	@ApiOperation(value="查询案例信息",httpMethod="POST")
	public Result  findCaseEditVO(Integer caseId){
		CaseEditVO caseEditVO=casesService.findCaseEditVO(caseId);
		return new Result("1", caseEditVO,"1");
	}	
	/**
	 * 更新案例信息
	 * @param CaseEditVO-案例封装对象
	 * @return
	 */
	@PostMapping("/updateCases")
	@ApiOperation(value="更新案例信息",httpMethod="POST")
	public  Result updateCases(CaseEditVO caseEditVO){
		return casesService.updateCases(caseEditVO);
	}
	
}
