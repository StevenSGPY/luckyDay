package com.imooc.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.o2o.dao.ProductSellDailyDao;
import com.imooc.o2o.entity.ProductSellDaily;
import com.imooc.o2o.service.ProductSellDailyService;
import com.imooc.o2o.web.wechat.WechatController;


@Service
public class ProductSellDailyServiceImpl implements ProductSellDailyService {
	private static Logger log = LoggerFactory.getLogger(WechatController.class);
	@Autowired
	private ProductSellDailyDao productSellDailyDao;
	
	@Override
	public void dailyCalculate() {
		log.info("Quartz Running");
		productSellDailyDao.insertProductSellDaily();
		productSellDailyDao.insertDefaultProductSellDaily();
		
	}

	@Override
	public List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime,
			Date endTime) {
		
		return productSellDailyDao.queryProductSellDaily(productSellDailyCondition, beginTime, endTime);
	}

}
