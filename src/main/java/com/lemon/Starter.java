package com.lemon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication   
@EnableSwagger2
@MapperScan(basePackages="com.lemon.mapper")//一次性给所有的dao层创建对象
@EnableTransactionManagement //启用数据库事务管理
public class Starter {
	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);
	}
}
