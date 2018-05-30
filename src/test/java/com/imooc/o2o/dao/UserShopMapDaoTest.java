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
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.UserShopMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserShopMapDaoTest{
	@Autowired
	private UserShopMapDao userShopMapDao;

	@Test
	public void testAInsertUserMap() throws Exception {
		UserShopMap userShopMap = new UserShopMap();
		PersonInfo customer = new PersonInfo();
		customer.setUserId(13L);
		userShopMap.setUser(customer);
		Shop shop = new Shop();
		shop.setShopId(28L);
		userShopMap.setShop(shop);
		userShopMap.setCreateTime(new Date());
		userShopMap.setPoint(1);
		int effectedNum = userShopMapDao.insertUserShopMap(userShopMap);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryUserShopMap() throws Exception {
		UserShopMap userShopMap = new UserShopMap();
		
		List<UserShopMap> userShopMapList = userShopMapDao
				.queryUserShopMapList(userShopMap, 0, 3);
		for (UserShopMap userShopMap2 : userShopMapList) {
			System.out.println(userShopMap2.getUserShopId());
		}
		int count = userShopMapDao.queryUserShopMapCount(userShopMap);
		System.out.println("数量："+count);
		//叠加店铺查询
		Shop shop = new Shop();
		shop.setShopId(29L);
		userShopMap.setShop(shop);
		userShopMapList = userShopMapDao.queryUserShopMapList(
				userShopMap, 0, 3);
		for (UserShopMap userShopMap2 : userShopMapList) {
			System.out.println("顾客店铺积分映射Id"+userShopMap2.getUserShopId());
		}
		count = userShopMapDao.queryUserShopMapCount(userShopMap);
		System.out.println("数量："+count);
		
		//按用户ID和店铺查询
		userShopMap = userShopMapDao.queryUserShopMap(1, 29);
		System.out.println(userShopMap.getUser().getName());
	}
	@Test
	public void testCUpdateUserShopMap() throws Exception{
		UserShopMap userShopMap = new UserShopMap();
		userShopMap = userShopMapDao.queryUserShopMap(1, 29);
		if (userShopMap.getPoint() == 1) {
			System.out.println("积分不一致");
		}
		userShopMap.setPoint(2);
		int num = userShopMapDao.updateUserShopMapPoint(userShopMap);
		System.out.println(num);
	}
}
