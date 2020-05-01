package com.lemon.handler;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler{
	/**
	 * 数据库插入数据时才会调用
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		this.setFieldValByName("regtime", new Date(), metaObject); //用户表注册时间
		this.setFieldValByName("createTime",new Date(), metaObject);//项目表、接口分类表 创建时间
		
	}
	/**
	 * 数据库更新数据时才会调用
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		// TODO Auto-generated method stub
		
	}
}
