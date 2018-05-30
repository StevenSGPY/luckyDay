package com.imooc.o2o.config.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * 对应spring-service里面的transactionManager
 * 继承TransactionManagementConfigurer是因为开启annotation-driven
 * @author zp
 *
 */
@Configuration
//首先使用注解@EnableTransactionManagement开启事务支持后
//在Service方法上添加注解@Transactional便可
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {

	@Autowired
	//注入DataSourceConfigu里面的dataSource，通过createDatasource（）获取
	private DataSource dataSource;
	
	/**
	 * 关于事务管理，需要返回PlatformTransactionManager的实现
	 */
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		
		return new DataSourceTransactionManager(dataSource);
	}

}
