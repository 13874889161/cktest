package com.lemon.service.impl;

import com.lemon.pojo.Api;
import com.lemon.pojo.ApiRequestParam;
import com.lemon.common.ApiListVO;
import com.lemon.common.ApiRunResult;
import com.lemon.common.ApiVO;
import com.lemon.common.Result;
import com.lemon.mapper.ApiMapper;
import com.lemon.service.ApiRequestParamService;
import com.lemon.service.ApiService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.apache.commons.collections.map.MultiValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {

	@Autowired
	ApiMapper apiMapper;
	
	@Autowired
	ApiRequestParamService apiRequestParamService;

	/**
	 * 业务层调用dao层--查询指定项目下接口列表
	 */
	@Override
	public List<ApiListVO> showApiListByProject(Integer projectId) {
		return apiMapper.showApiListByProject(projectId);
	}

	/**
	 * 业务层调用dao层 --查询指定分类下接口
	 */
	@Override
	public List<ApiListVO> showApiListByclassificationId(Integer classificationId) {
		return apiMapper.showApiListByclassificationId(classificationId);
	}

	/**
	 * 业务层调用dao层-查询指定接口信息
	 */
	@Override
	public ApiVO findApiViewVO(Integer apiId) {
		return apiMapper.findApiViewVO(apiId);
	}

	/**
	 * 远程调用运行接口 apiVO--接口参数对象
	 */
	@Override
	public ApiRunResult run(ApiVO apiVO) {
		// 1、创建RestTemplate对象，此对象拥有处理http请求的能力
		RestTemplate restTemplate = new RestTemplate();
		// 2、准备请求需要的相关数据
		// 2-1 基本信息
		String url = apiVO.getHost() + apiVO.getUrl();// url
		String method = apiVO.getMethod();// 请求方式
		List<ApiRequestParam> paramList = apiVO.getRequestParams(); // 获取请求体
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
		ApiRunResult apiRunResult = new ApiRunResult();// 3-3：自定义的响应返回格式对象
		try {
			// exchange统一调用的方法，支持post get put delete 等
			if ("get".equalsIgnoreCase(method)) {// equalsIgnoreCase忽略大小写
				// get请求时只需要请求头就可以了；参数在url上进行拼接
				httpEntity = new HttpEntity(headers);
				response = restTemplate.exchange(url + paramStr, HttpMethod.GET, httpEntity, String.class);
			} else if ("post".equalsIgnoreCase(method)) {
				if ("".equals(jsonParam)) {
					httpEntity = new HttpEntity(bodyParams, headers);
				} else {
					httpEntity = new HttpEntity(jsonParam, headers);
				}
				response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
			} else if ("put".equalsIgnoreCase(method)) {

			} else if ("delete".equalsIgnoreCase(method)) {

			}
			apiRunResult.setStatusCode(response.getStatusCodeValue() + "");
			// 响应头是一个对象，需要转换成功json字符串；使用fastjson
			// 方式1 用jackjson:ObjectMapper().writeValueAsString(response.getHeaders());
			// 方式2 fastjson
			apiRunResult.setHeaders(JSON.toJSONString(response.getHeaders()));
			// 响应体
			if(response.getBody()!=null) apiRunResult.setBody(response.getBody().toString());
		} catch (HttpStatusCodeException e) {// HttpStatusCodeException专门捕获http远程调用的异常
			// 如果出现异常获取异常对应信息
			apiRunResult.setStatusCode(e.getRawStatusCode() + "");
			apiRunResult.setHeaders(JSON.toJSONString(e.getResponseHeaders()));
			apiRunResult.setBody(e.getResponseBodyAsString());
		}

		return apiRunResult;
	}
	/**
	 * 更新接口信息
	 */
	@Override
	public Result edit(ApiVO apiVo) {
		//直接根据主键更新api基本信息
				this.updateById(apiVo);
				
				QueryWrapper queryWrapper=new QueryWrapper();
				queryWrapper.eq("api_id", apiVo.getId());
				//为了让前端分门别类的渲染，将参数分成了四个部分传给后端
				//将四个部分的参数全部汇总到RequestParams中  addAll往list中追加多个参数
				//将参数属性为null的数据先删除
				List<ApiRequestParam> apiRequestParams=apiVo.getRequestParams();
				apiRequestParams.addAll(apiVo.getQueryParams()); //type=1 Query参数 url路径三的
				apiRequestParams.addAll(apiVo.getBodyParams());  //type=2 body参数
				apiRequestParams.addAll(apiVo.getHeaderParams()); //type=3 头部参数
				apiRequestParams.addAll(apiVo.getBodyRawParams()); //type=4  json格式参数
				List<ApiRequestParam>  apiRequestParamAll=apiRequestParamService.list(queryWrapper);
				//与前端传的数据进行比较,删除数据库多余的数据
				for (ApiRequestParam apiRequestParam : apiRequestParamAll) {
					Integer apiRequestParamId=apiRequestParam.getId();
					boolean flge=false;
					for (ApiRequestParam apiRequestParam2 : apiRequestParams) {
						if(apiRequestParamId.equals(apiRequestParam2.getId())){
							flge=true;
							break;
						}
					}
					if(!flge){
						apiRequestParamService.removeById(apiRequestParamId);
					}
				}
				try {
					if(apiRequestParams.size()>0) apiRequestParamService.saveOrUpdateBatch(apiRequestParams);
				} catch (Exception e) {
					//TODO    这一段还有问题  后面再修复!!!!!!!!!!!!
					return new Result("0","参数名重复");
				}
				return new Result("1","保存成功");	
	}

}
