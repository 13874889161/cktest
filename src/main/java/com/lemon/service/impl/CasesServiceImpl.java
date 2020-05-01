package com.lemon.service.impl;

import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.CaseParamValue;
import com.lemon.pojo.Cases;
import com.lemon.pojo.TestRule;
import com.lemon.pojo.User;
import com.lemon.common.ApiVO;
import com.lemon.common.CaseEditVO;
import com.lemon.common.CasesListVO;
import com.lemon.common.Result;
import com.lemon.mapper.CasesMapper;
import com.lemon.service.CaseParamValueService;
import com.lemon.service.CasesService;
import com.lemon.service.TestRuleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
public class CasesServiceImpl extends ServiceImpl<CasesMapper, Cases> implements CasesService {
	@Autowired
	CaseParamValueService caseParamValueService ;
	
	@Autowired
	CasesMapper casesMapper;
	
	@Autowired
	TestRuleService testRuleService;
	/**
	 * 添加测试案例的方法
	 * @param cases
	 * @param apiVO
	 * @return
	 */
	@Override
	public Result add(Cases cases,ApiVO apiVO){
		//添加cases中
		Subject subject=SecurityUtils.getSubject();
		User user=(User) subject.getPrincipal();
		cases.setCreateUser(user.getId());
		cases.setApiId(apiVO.getId());
		this.save(cases); //插入主键的同时会保存到对象中
		//System.out.println(2/0);
		List<ApiRequestParam>  requestParamList=apiVO.getRequestParams();
		List<CaseParamValue> caseParamValues=new ArrayList<CaseParamValue>();
		for (ApiRequestParam requestParam : requestParamList) {
			CaseParamValue caseParamValue=new CaseParamValue();
			caseParamValue.setCaseId(cases.getId());//插入后主键会自动保存到对象中，可以直接进行获取
			caseParamValue.setApiRequestParamId(requestParam.getId());
			caseParamValue.setApiRequestParamValue(requestParam.getValue());
			caseParamValue.setCreateUser(user.getId());
			caseParamValues.add(caseParamValue);
		}
		caseParamValueService.saveBatch(caseParamValues);
		//添加到caseParamValue中
		return new Result("1", "添加成功");
	}
	
	/**
	 * 查询项目下所有 case信息/拼接api信息的方法
	 */
	@Override
	public List<CasesListVO> showCaseUnderProject(Integer projectId) {
		return casesMapper.showCaseUnderProject(projectId);
	}
	
	/**
	 * 查询套件下所有测试案例/拼接api信息的方法
	 */
	@Override
	public List<CasesListVO> showCaseUnderSuiteId(Integer suiteId) {
		return casesMapper.showCaseUnderSuiteId(suiteId);
	}
	
	/**
	 * 通过caseId查询对应案例信息
	 */
	@Override
	public CaseEditVO findCaseEditVO(Integer caseId) {
		return casesMapper.findCaseEditVO(caseId);
	}
	/**
	 * 更新case信息
	 */
	@Override
	public Result updateCases(CaseEditVO caseEditVO) {
		try {
			User user=(User) SecurityUtils.getSubject().getPrincipal();
			QueryWrapper queryWrapper=new QueryWrapper();
			queryWrapper.eq("case_id", caseEditVO.getId());
			//1、先更新cases表
			this.updateById(caseEditVO);
			//2、再更新casaeparamvalue表  --有新增删除更新等操作
			updateCaseParamValueTable(caseEditVO, user, queryWrapper);
			//3、更新测试断言表
			updateTestRuleTable(caseEditVO,user,queryWrapper);
			return new Result("1","更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("1","更新失败");
			
		}
	}
	/**
	 * 更新TestRule表
	 * @param caseEditVO
	 * @param user
	 * @param queryWrapper
	 */
	private void updateTestRuleTable(CaseEditVO caseEditVO, User user, QueryWrapper queryWrapper) {
		System.out.println(caseEditVO.getTestRules());
		List<TestRule> testRules=caseEditVO.getTestRules();
		List<TestRule>  TestRuleAll=testRuleService.list(queryWrapper);//查询现有cases表中参数
		//a、删除多余数据
		for (TestRule testRuletb : TestRuleAll) {
			Integer testRuleId=testRuletb.getId();
			boolean flge=false;
			for (TestRule testRule : testRules) {
				if(testRuleId.equals(testRule.getId())){
					flge=true;
					break;
				}
			}
			if(!flge){
				testRuleService.removeById(testRuleId);
			}
		}
		//b、更新或者新增
//		List<CaseParamValue> updateCaseParam=null;
		for(TestRule testRule : testRules){
			testRule.setCaseId(caseEditVO.getId());
			testRule.setCreateUser(user.getId());
//			updateCaseParam.add(caseParamValue);
		}
		if(testRules.size()>0) testRuleService.saveOrUpdateBatch(testRules);
		
	}

	/**
	 * 更新CaseParamValue表
	 * @param caseEditVO
	 * @param user
	 * @param queryWrapper
	 */
	private void updateCaseParamValueTable(CaseEditVO caseEditVO, User user, QueryWrapper queryWrapper) {
		List<ApiRequestParam> apiRequestParams=caseEditVO.getRequestParams();
		List<CaseParamValue>  caseParamAll=caseParamValueService.list(queryWrapper);//查询现有cases表中参数
		//a、删除多余数据
		for (CaseParamValue caseParam : caseParamAll) {
			Integer caseParamId=caseParam.getId();
			boolean flge=false;
			for (ApiRequestParam apiRequestParam : apiRequestParams) {
				if(caseParamId.equals(apiRequestParam.getValueId())){
					flge=true;
					break;
				}
			}
			if(!flge){
				caseParamValueService.removeById(caseParamId);
			}
		}
		//b、更新或者新增
		List<CaseParamValue> updateCaseParamList=new ArrayList<CaseParamValue>();
		for(ApiRequestParam apiRequestParam : apiRequestParams){
			CaseParamValue caseParamValue=new CaseParamValue();
			caseParamValue.setId(apiRequestParam.getValueId());
			caseParamValue.setCaseId(caseEditVO.getId());
			caseParamValue.setApiRequestParamId(apiRequestParam.getId());
			caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
			caseParamValue.setCreateUser(user.getId());
			updateCaseParamList.add(caseParamValue);
		}
		if(updateCaseParamList.size()>0)  caseParamValueService.saveOrUpdateBatch(updateCaseParamList);
	}
	/**
	 * 查询项目下所有的测试案例
	 */
	@Override
	public List<CaseEditVO> findCaseByPorject(Integer projectId) {
		return casesMapper.findCaseByPorject(projectId);
	}
	
	/**
	 * 查询套件下所有的测试案例
	 */
	@Override
	public List<CaseEditVO> findCaseBySuite(Integer suiteId) {
		return casesMapper.findCaseBySuite(suiteId);
	}
}
