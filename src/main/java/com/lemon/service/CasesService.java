package com.lemon.service;

import com.lemon.common.ApiVO;
import com.lemon.common.CaseEditVO;
import com.lemon.common.CasesListVO;
import com.lemon.common.Result;
import com.lemon.pojo.Cases;

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
public interface CasesService extends IService<Cases> {

	
	/**
	 * 添加测试案例的方法
	 * @param cases
	 * @param apiVO
	 * @return
	 */
	public Result add(Cases cases,ApiVO apiVO);
	/**
	 * 查询项目下所有测试案例/拼接接口信息
	 * @param projectId
	 * @return
	 */
	List<CasesListVO> showCaseUnderProject(Integer projectId);
	
	/**
	 * 查询套件下所有测试案例的方法/拼接接口信息
	 * @param suiteId
	 * @return
	 */
	List<CasesListVO> showCaseUnderSuiteId(Integer suiteId);
	
	/**
	 * 通过caseId查询案例相关信息/接口/参数/项目信息等
	 * @param caseId
	 * @return
	 */
	CaseEditVO findCaseEditVO(Integer caseId);
	/**
	 * 更新案例
	 * @param caseEditVO
	 * @return
	 */
	Result updateCases(CaseEditVO caseEditVO);
	
	/**
	 * 查询项目下测试案例--用来执行
	 * @param projectId
	 * @return
	 */
	List<CaseEditVO> findCaseByPorject(Integer projectId);
	
	/**
	 * 查询套件下测试案例--用来执行
	 * @param suiteId
	 * @return
	 */
	List<CaseEditVO> findCaseBySuite(Integer suiteId);
}
