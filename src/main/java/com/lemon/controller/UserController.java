package com.lemon.controller;

import java.util.Date;

import javax.websocket.Session;

import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.common.Result;
import com.lemon.pojo.User;
import com.lemon.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qjf
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/user")
@Api("用户模块")
@CrossOrigin //解决前后端跨域的问题--使用公共过滤器时，此注解已经弃用
public class UserController {

	@Autowired
	 private UserService userService;
	/**
	 *注册方法
	 * @param user 请求带过来的用户对象
	 * @return
	 */
	//@RequestMapping("/register")
	@PostMapping("/register")  //只能使用post方式进行请求
	@ApiOperation(value="注册方法",httpMethod="POST")
	public Result register(User user) {
		//返回boolean类数据，但是这里因为一般都会插入成功，所以一般使用异常捕获的方式判断是否插入成功就可以了
		userService.save(user); //在数据库中插入数据
		return new Result("1", "注册成功");//返回result对象，并通过@RestController注解转换成功json格式
	}

	/**
	 * 注册用户验证重复的方法
	 * @param username
	 * @return
	 */
	@GetMapping("/find")
	@ApiOperation(value="账号验证重复",httpMethod="GET")
	public Result find(String username) {
		Result result=null;
		QueryWrapper queryWrapper=new QueryWrapper(); //封装查询条件的对象
		queryWrapper.eq("username", username);//添加查询条件
		User user=userService.getOne(queryWrapper);//用非主键查询数据
		if (user==null){
			result=new Result("1", "账号不存在");
		}else{
			result=new Result("0", "账号已存在");
		}
		return result;
	}
	/**
	 * 登录方法
	 * 验证账户安全的部分交给shiro去完成
	 * @param user
	 * @return
	 */
	@PostMapping("/login")  //只能使用post方式进行请求
	@ApiOperation(value="登录方法",httpMethod="POST")
	public Result login(User user) {
		Result result = null;;
		try {
			//通过用户名密码得到token
			UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());
			Subject subject=SecurityUtils.getSubject();//得到操作对象
			subject.login(token);//使用shiro开始验证这个token是否安全有效
			//通过操作对象得到sessionId
			String sessionId=(String) subject.getSession().getId();
			User loginUser=(User) subject.getPrincipal();//getPrincipal方法得到登录的用户信息
			result=new Result("1", loginUser.getId(),sessionId);
		} catch (AuthenticationException e) {
			if(e instanceof UnknownAccountException){
				result=new Result("0", "用户名错误");
			}else{
				result=new Result("0","密码错误");
			}
			e.printStackTrace();
		}
		return result;//返回result对象，并通过@RestController注解转换成功json格式
	}
	
	/**
	 * 登出方法
	 * @return
	 */
	@GetMapping("/logout")
	@ApiOperation(value="登出方法",httpMethod="GET")
	public Result logout() {
		SecurityUtils.getSubject().logout(); //交给shiro管理
		return new Result("1", "账号未登录");
	}
	
	/**
	 * 未授权方法
	 *发现页面并没有登录，但是却对页面进行操作时
	 * @return
	 */
	@GetMapping("/unauth")
	@ApiOperation(value="未授权方法",httpMethod="GET")
	public Result unauth() {
		return new Result("1", "账号未登录");
	}
}
