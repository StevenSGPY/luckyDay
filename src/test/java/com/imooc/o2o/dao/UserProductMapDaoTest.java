package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserProductMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserProductMapDaoTest{
	@Autowired
	private UserProductMapDao userProductMapDao;

	@Test
	public void testAInsertUserProductMap() throws Exception {
		UserProductMap userProductMap = new UserProductMap();
		PersonInfo customer = new PersonInfo();
		customer.setUserId(1L);
		userProductMap.setUser(customer);
		userProductMap.setOperator(customer);
		Product product = new Product();
		product.setProductId(1L);
		userProductMap.setProduct(product);
		Shop shop = new Shop();
		shop.setShopId(29L);
		userProductMap.setShop(shop);
		userProductMap.setCreateTime(new Date());
		int effectedNum = userProductMapDao
				.insertUserProductMap(userProductMap);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryUserProductMapList() throws Exception {
		UserProductMap userProductMap = new UserProductMap();
		PersonInfo customer = new PersonInfo();
		customer.setName("test");
		List<UserProductMap> userProductMapList = userProductMapDao
				.queryUserProductMapList(userProductMap, 0, 3);
		for (UserProductMap userProductMap2 : userProductMapList) {
			System.out.println(userProductMap2.getUserProductId());
		}
		int count = userProductMapDao.queryUserProductMapCount(userProductMap);
		System.out.println("数量："+count);
		//叠加店铺查询
		Shop shop = new Shop();
		shop.setShopId(29L);
		userProductMap.setShop(shop);
		userProductMapList = userProductMapDao.queryUserProductMapList(
				userProductMap, 0, 3);
		for (UserProductMap userProductMap2 : userProductMapList) {
			System.out.println("返回用户商品映射信息ID"+userProductMap2.getUserProductId());
		}
		count = userProductMapDao.queryUserProductMapCount(userProductMap);
		System.out.println("数量："+count);
	}
}
