package com.lemon.controller;


import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.Result;
import com.lemon.pojo.Project;
import com.lemon.pojo.User;
import com.lemon.service.ProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author qjf
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/project")
@Api("项目模块")
@CrossOrigin
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	/**
	 * 查询项目列表
	 * @param userId 通过userid查询
	 * @return
	 */
	@GetMapping("/toList")
	@ApiOperation(value="查询项目列表",httpMethod="GET")
	public Result toList(Integer userId){
		QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("create_user",userId);
		//查询结果为list时使用list方法
		List<Project> projectList=projectService.list(queryWrapper); 
		Result result=new Result("1", projectList, "项目列表");
		return  result;
	}
	/**
	 * 新增项目方法
	 * @param project
	 * @return
	 */
	@PostMapping("/addProject")
	@ApiOperation(value="新增项目",httpMethod="POST")
	public  Result addProject(Project project){
		Result result=null;
		//因为前段传入的project对象的不会有userId；所以需要自己获取
		User user=(User) SecurityUtils.getSubject().getPrincipal();
		QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("name", project.getName());
		Project selectProject=projectService.getOne(queryWrapper);
		if(selectProject !=null){
			result=new Result("2", "项目已存在");
		}else{
			project.setCreateUser(user.getId());
			projectService.save(project);
			result=new Result("1","添加成功");
			
		}
		return result;
	}
	
	/**
	 * 查询项目信息方法
	 * 使用rest风格访问路径
	 */
	@GetMapping("/{projectId}")
	@ApiOperation(value="查询项目信息",httpMethod="GET")
	public Result getPorjectById(@PathVariable("projectId") Integer projectId){
		Project project=projectService.getById(projectId);
		return new Result("1",project,"项目信息");
	}
	
	/**
	 * 更新项目信息
	 * 使用rest风格访问路径
	 */
	@PostMapping("/{projectId}")
	@ApiOperation(value="更新项目信息",httpMethod="POST")
	public Result updatePorject(@PathVariable("projectId") Integer projectId,Project project){
		project.setId(projectId);
		User user=(User) SecurityUtils.getSubject().getPrincipal();
		project.setCreateUser(user.getId());
		projectService.updateById(project);	//通过主键更新列	
		return new Result("1","保存成功");
	}
	
	/**
	 * 删除项目
	 */
	@DeleteMapping("/{projectId}")
	@ApiOperation(value="删除项目",httpMethod="DELETE")
	public  Result deletePorject(@PathVariable("projectId") Integer projectId){
		projectService.removeById(projectId);
		return new Result("1","删除成功");
	}
	
}
