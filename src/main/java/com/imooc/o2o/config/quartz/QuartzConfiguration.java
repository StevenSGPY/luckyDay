package com.imooc.o2o.config.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.imooc.o2o.service.ProductSellDailyService;

@Configuration
public class QuartzConfiguration {
	@Autowired
	private ProductSellDailyService productSellDailyService;
	
	@Autowired
	private MethodInvokingJobDetailFactoryBean jobDetailFactory;
	
	@Autowired
	private CronTriggerFactoryBean productSellDailyTriggerFactory;
	/**
	 * 创建jobDetailFactory并返回
	 * @return
	 */
	@Bean(name="jobDetailFactory")
	public MethodInvokingJobDetailFactoryBean createJobDetail() {
		//new出来的JobDetailFactory对象，此工厂主要用来制作一个JobDetail，即制作一个任务
		MethodInvokingJobDetailFactoryBean jobDetailFactory = new MethodInvokingJobDetailFactoryBean();
		//设置名字
		jobDetailFactory.setName("product_sell_daily_job");
		//设置组名
		jobDetailFactory.setGroup("job_product_sell_daily_group");
		//对于多个相同的jobDetail，当指定多个Trigger时，很可能第一个job未完成之前，第二个job就开始执行了
		//指定concurrent设为false，多个job不会并发运行，第二个job将不会在第一个job完成之前执行
		jobDetailFactory.setConcurrent(false);
		//指定运行的类
		jobDetailFactory.setTargetObject(productSellDailyService);
		//指定运行的方法
		jobDetailFactory.setTargetMethod("dailyCalculate");
		return jobDetailFactory;
	}
	
	/**
	 * 创建productSellDailyTriggerFactory并返回
	 * @return
	 */
	@Bean(name="productSellDailyTriggerFactory")
	public CronTriggerFactoryBean createProductSellDailyTrigger() {
		//创建CronTriggerFactoryBean的实例，用来创建trigger
		CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
		//设置trigger的名字
		triggerFactory.setName("product_sell_daily_trigger");
		//设置trigger工作组
		triggerFactory.setGroup("job_product_sell_daily_group");
		//绑定jobDetail
		triggerFactory.setJobDetail(jobDetailFactory.getObject());
		//设定cron表达式
		triggerFactory.setCronExpression("0 0 0 * * ? *");
		return triggerFactory;
	}
	/**
	 * 创建调度工厂并返回
	 * @return
	 */
	@Bean(name="schedulerFactory")
	public SchedulerFactoryBean createSchedulerFactory() {
		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		schedulerFactory.setTriggers(productSellDailyTriggerFactory.getObject());
		return schedulerFactory;
	}
}
