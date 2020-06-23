package com.lemon.service.impl;

import com.lemon.pojo.ApiRequestParam;
import com.lemon.pojo.ReportRecord;
import com.lemon.pojo.TestReport;
import com.lemon.pojo.TestRule;
import com.lemon.pojo.User;
import com.lemon.common.ApiRunResult;
import com.lemon.common.CaseEditVO;
import com.lemon.mapper.CasesMapper;
import com.lemon.mapper.TestReportMapper;
import com.lemon.service.CasesService;
import com.lemon.service.ReportRecordService;
import com.lemon.service.TestReportService;
import com.lemon.utils.AssertRuleUtil;
import com.lemon.utils.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
@Service
public class TestReportServiceImpl extends ServiceImpl<TestReportMapper, TestReport> implements TestReportService {
	@Autowired
	CasesService casesService;
	@Autowired
	ReportRecordService reportRecordService;
	@Autowired
	TestReportService testReportService;
	@Autowired
	TestReportMapper testReportMapper;
	
	/**
	 * 运行测试案例方法
	 * suiteId为空时则运行整个项目下所有案例
	 * 如果不为空，则运行单个套件案例
	 * projectId:项目id
	 * suiteId:套件id
	 */
	@Override
	public List<TestReport> runTest(Integer projectId, Integer suiteId) {
		//1、先查询--需要测试
		List<CaseEditVO> CaseList=new ArrayList<CaseEditVO>();
		String testTypeName="";
		if(suiteId==null){
			CaseList=casesService.findCaseByPorject(projectId);//获取整个项目下的案例
			testTypeName=CaseList.get(0).getProjectName()+"项目";
		}else {
			CaseList=casesService.findCaseBySuite(suiteId);//获取单个套件下的案例
			testTypeName=CaseList.get(0).getSuiteName()+"套件";
		}
		//System.out.println("1111111111:"+CaseList);
		//2、插入报告记录表
		//String projectName=CaseList.get(0).getProjectName();
		ReportRecord reportRecord=new ReportRecord();
		reportRecord.setProjectId(projectId);//项目id
		User user=(User) SecurityUtils.getSubject().getPrincipal();
		reportRecord.setReptName(DateUtil.getYMDString2(new Date())+testTypeName+"测试报告");
		reportRecord.setCreateUser(user.getId());
		reportRecordService.save(reportRecord);//生成主键后会赋值到此对象中
		//3循环执行案例-生成报告
		List<TestReport> reportList=new ArrayList<TestReport>();
		for (CaseEditVO caseEditVO : CaseList) {
			TestReport testReport=runAdnGetReport(caseEditVO);//运行测试
			testReport.setReptId(reportRecord.getReptId());//
			reportList.add(testReport);
			
		}
		//4、对test_report表进行操作 直接插入
		testReportService.saveBatch(reportList);
		return reportList;
	}
	/**
	 * 运行单个案例的方法
	 * @param caseEditVO
	 * @return
	 */
	TestReport runAdnGetReport(CaseEditVO caseEditVO){
			TestReport testReport=new TestReport();
		// 1、创建RestTemplate对象，此对象拥有处理http请求的能力
				RestTemplate restTemplate = new RestTemplate();
				// 2、准备请求需要的相关数据
				// 2-1 基本信息
				String url = caseEditVO.getHost() + caseEditVO.getUrl();// url
				String method = caseEditVO.getMethod();// 请求方式
				List<ApiRequestParam> paramList = caseEditVO.getRequestParams(); // 获取请求体
				// 2-2参数:需要用MultiValueMap进行传递
				// a、请求头
				LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
				// b、请求体
				LinkedMultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<String, String>();
				// 2-3、将前端获取的参数分别设置到请求头和请求体中
				// get请求需单独处理--思路:将参数拼接到url上
				String paramStr = "?";
				String jsonParam = "";
				for (ApiRequestParam apiRequestParam : paramList) {
					if (apiRequestParam.getType() == 3) {
						// 类型3--请求头
						headers.add(apiRequestParam.getName(), apiRequestParam.getValue());
					} else if (apiRequestParam.getType() == 1) {
						// 类型1--get请求
						paramStr += apiRequestParam.getName() + "=" + apiRequestParam.getValue() + "&";
						//paramStr.contains(s)
					} else if (apiRequestParam.getType() == 2) {
						// 类型2
						bodyParams.add(apiRequestParam.getName(), apiRequestParam.getValue());
					} else if (apiRequestParam.getType() == 4) {
						// 类型4:直接使用json字符串即可，不需要使用MultiValueMap格式传入
						jsonParam = apiRequestParam.getValue();
					}
				}
				if (!"?".equals(paramStr)) {
					paramStr = paramStr.substring(0, paramStr.lastIndexOf("&"));
				}
				// 3、整合参数信息，发送请求得到响应结果
				HttpEntity httpEntity = null;// 3-1、请求实体对象:需要放入请求体和请求头数据
				ResponseEntity response = null;// 3-2、接收响应信息
				//ApiRunResult apiRunResult = new ApiRunResult();// 3-3：自定义的响应返回格式对象
				try {
					// exchange统一调用的方法，支持post get put delete 等
					if ("get".equalsIgnoreCase(method)) {// equalsIgnoreCase忽略大小写
						// get请求时只需要请求头就可以了；参数在url上进行拼接
						httpEntity = new HttpEntity(headers);
						response = restTemplate.exchange(url + paramStr, HttpMethod.GET, httpEntity, String.class);
						testReport.setRequestUrl(url + paramStr);
					} else if ("post".equalsIgnoreCase(method)) {
						if ("".equals(jsonParam)) {
							httpEntity = new HttpEntity(bodyParams, headers);
							testReport.setRequestBody(JSON.toJSONString(bodyParams));//请求体
						} else {
							httpEntity = new HttpEntity(jsonParam, headers);
							testReport.setRequestBody(jsonParam);//请求体json格式
						}
						testReport.setRequestUrl(url);
						response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
					} else if ("put".equalsIgnoreCase(method)) {

					} else if ("delete".equalsIgnoreCase(method)) {

					}
					
					testReport.setCaseId(caseEditVO.getId());//caseID
					testReport.setRequestHeaders(JSON.toJSONString(headers));//请求头
					testReport.setResponseHeaders(JSON.toJSONString(response.getHeaders()));//响应头
					String responseBody="";
					if(response.getBody()!=null){
					 responseBody=response.getBody().toString();
					}
					testReport.setResponseBody(responseBody);//请求体
					testReport.setPassFlag(assertByTestRule(responseBody,caseEditVO.getTestRules()));//断言结果
				}catch(Exception e){
					e.printStackTrace();
				}
		
				return testReport;
	}
	/**
	 * 断言的方法后续再优化
	 * @param response
	 * @param testRules
	 * @return
	 */
	String assertByTestRule(String responseBody,List<TestRule> testRules){
		boolean flag=true;
		boolean testResult=false;
		for (TestRule testRule : testRules) {
			if(testRule.getRule()==1){//1-包含断言
				testResult=AssertRuleUtil.assertByContains(responseBody, testRule.getExpected());
			}else if(testRule.getRule()==2){//2-不包含断言
				testResult=AssertRuleUtil.assertByNotContains(responseBody, testRule.getExpected());
			}else if(testRule.getRule()==3){//3、jasonpathValue
				AssertRuleUtil.assertByJsonPathValue(responseBody, testRule.getExpression(), testRule.getOperatorName(), testRule.getExpected());
			}else if(testRule.getRule()==4){//4、jsonpathcount
				testResult=AssertRuleUtil.assertByJsonPathCount(responseBody, testRule.getExpression(), testRule.getExpected());
			}else if(testRule.getRule()==5){//5、等于
				testResult=AssertRuleUtil.assertByEquals(responseBody, testRule.getExpected());
			}else if(testRule.getRule()==6){//6、不等于
				testResult=AssertRuleUtil.assertByNotEquals(responseBody, testRule.getExpected());
			}else if(testRule.getRule()==7){//7、脚本断言
				//TODO  待开发！！！！！！！！！！
				//AssertRuleUtil.assertByScript(responseBody, testRule.getExpected());
			}
			//如果上面有失败的
			if(!testResult){
				flag=false;
			}
		}
		if(!flag){
			return "failed";
		}
		return "succeed";
	}
	/**
	 * 获取案例最新的测试结果
	 */
	@Override
	public TestReport findCaseRunResult(Integer caseId) {
		return testReportMapper.findReportByCase(caseId);
	}

}
		
		


