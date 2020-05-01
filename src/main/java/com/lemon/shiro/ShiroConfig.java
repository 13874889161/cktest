package com.lemon.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**shiro的配置
 * @author Administrator
 *
 */
@Configuration
public class ShiroConfig {
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断
		
		filterChainDefinitionMap.put("/user/login", "anon");
		filterChainDefinitionMap.put("/user/find", "anon");
		filterChainDefinitionMap.put("/user/register", "anon");
		//swagger2放行
		filterChainDefinitionMap.put("/swagger-ui.html", "anon");
		filterChainDefinitionMap.put("/swagger/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**", "anon");
		filterChainDefinitionMap.put("/v2/**", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		filterChainDefinitionMap.put("/configuration/**", "anon");
		// 过滤链定义，从上向下顺序执行
		// authc:url都必须认证通过才可以访问; anon:url都可以匿名访问
		filterChainDefinitionMap.put("/**", "authc");
	
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		// 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
		shiroFilterFactoryBean.setLoginUrl("/user/unauth");
		return shiroFilterFactoryBean;
	}

	// 重新设置SecurityManager，通过自动以的MyRealm完成登录校验：
	@Bean  //@Bean注解的含义将方法返回值放到ioc容器中
	public MyRealm myReal() {
		return new MyRealm();
	}

	@Bean
	public SecurityManager securityManager(MyRealm myReal) { //此方法的MyRealm对象，是当容器中存在就会自动注入进来(di 依赖注入)
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();//生成securityManager对象
		 //自定义session管理;交给securityManager管理
       securityManager.setSessionManager(sessionManager());
		// 设置realm
		securityManager.setRealm(myReal);  //将MyRealm对象告诉securityManager
		return securityManager;
	}
	//将CustomSessionManager对象放到spring容器中然后交给securityManagerguanli 
	@Bean
	public SessionManager sessionManager(){
		CustomSessionManager manager = new CustomSessionManager();
	        manager.setSessionDAO(new EnterpriseCacheSessionDAO());
		return manager;
	}



}
