package com.imooc.o2o.config.dao;

import java.beans.PropertyVetoException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.imooc.o2o.util.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 配置datasource到ioc容器里面
 * @author zp
 *
 */
@Configuration
@MapperScan("com.imooc.o2o.dao")
public class DataSourceConfiguration {
	@Value("${jdbc.driver}")
	private String jdbcDriver;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	
	
	/**
	 * 生成与spring-dao.xml对应的bean dataSource
	 * @return
	 * @throws PropertyVetoException 
	 */
	@Bean(name="dataSource")
	public ComboPooledDataSource createDataSource() throws PropertyVetoException {
		//生成dataSource实例
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		//跟配置文件一样设置一下信息
		//驱动
		dataSource.setDriverClass(jdbcDriver);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUser(DESUtil.getDecryptString(jdbcUsername));
		dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));
		// c3p0连接池的私有属性 
		//连接池最大线程数
		dataSource.setMaxPoolSize(30);
		//连接池最小线程数
		dataSource.setMinPoolSize(10);
		//关闭连接后不自动commit
		dataSource.setAutoCommitOnClose(false);
		//获取连接超时时间
		dataSource.setCheckoutTimeout(10000);
		//当获取连接失败重试次数
		dataSource.setAcquireRetryAttempts(2);
		return dataSource;
		
	}
	
}
