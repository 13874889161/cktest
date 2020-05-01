package com.lemon.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lemon.pojo.User;
import com.lemon.service.UserService;

public class MyRealm extends AuthorizingRealm {
	
	@Autowired  //注入对象
	UserService userService; 
	/**
	 *授权(权限管理)：权限,角色管理
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 *  身份认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//通过login方法传入的token得到用户名
		//这里的token是控制层调用subject对象调用login方法获取
		String username=token.getPrincipal().toString();
		//通过用户名查询数据库,进行比对
		QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
		queryWrapper.eq("username", username); //封装查询条件
		User dbUser=userService.getOne(queryWrapper);
		if(dbUser != null){
//			if(dbUser.getPassword().equals(token.getCredentials())){}
			//SimpleAuthenticationInfo对象(对查询结果的密码校验)的三个参数
			//1、Object principal：可以是用户名 也可以是 User对象
			//2、 Object credentials: 密码
			//3、String realmName):MyRealm的名字
			return new SimpleAuthenticationInfo(dbUser, dbUser.getPassword(), getName());
		}
		return null;
	}

}
