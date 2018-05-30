package com.imooc.o2o.dao;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.o2o.entity.ProductSellDaily;
import com.imooc.o2o.entity.Shop;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductSellDailyDaoTest {
	
	@Autowired
	private ProductSellDailyDao productSellDailyDao;
	
	
	/**
	 * 测试添加商品日销量统计
	 * @throws Exception
	 */
	@Test
	public void testAInsertProductSellDaily() throws Exception{
		//创建商品日销量统计
		int num = productSellDailyDao.insertProductSellDaily();
		System.out.println(num);
	}
	
	@Test
	public void testBQueryProductSellDaily() throws Exception{
		ProductSellDaily productSellDaily = new ProductSellDaily();
		//叠加店铺查询
		Shop shop = new Shop();
		shop.setShopId(29L);
		productSellDaily.setShop(shop);
		List<ProductSellDaily> productSellDailyList =productSellDailyDao.queryProductSellDaily(productSellDaily,null,null);
		for (ProductSellDaily productSellDaily2 : productSellDailyList) {
			System.out.println(productSellDaily2.getProduct().getProductName());
		}
	}
	@Test
	public void testCInsertDefaultProductSellDaily() throws Exception{
		//创建商品日销量统计
		int num = productSellDailyDao.insertDefaultProductSellDaily();
		System.out.println(num);
	}
	
}
