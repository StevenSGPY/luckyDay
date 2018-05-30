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

import com.imooc.o2o.entity.Award;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AwardDaoTest{

	@Autowired
	private AwardDao awardDao;

	@Test
	public void testAInsertAward() throws Exception {
		long shopId = 1;
		Award award = new Award();
		award.setAwardName("测试一");
		award.setAwardImg("test1");
		award.setPoint(5);
		award.setPriority(1);
		award.setEnableStatus(1);
		award.setCreateTime(new Date());
		award.setLastEditTime(new Date());
		award.setShopId(shopId);
		int effectedNum = awardDao.insertAward(award);
		assertEquals(1, effectedNum);
		Award award2 = new Award();
		award2.setAwardName("测试二");
		award2.setAwardImg("test2");
		award2.setPoint(2);
		award2.setPriority(2);
		award2.setEnableStatus(0);
		award2.setCreateTime(new Date());
		award2.setLastEditTime(new Date());
		award2.setShopId(shopId);
		int num = awardDao.insertAward(award2);
		assertEquals(1, num);
	}

	@Test
	public void testBQueryAwardList() throws Exception {
		Award award = new Award();
		List<Award> awardList = awardDao.queryAwardList(award, 0, 3);
		assertEquals(2, awardList.size());
		int count = awardDao.queryAwardCount(award);
		assertEquals(2, count);
		award.setAwardName("测试");
		awardList = awardDao.queryAwardList(award, 0, 3);
		assertEquals(2, awardList.size());
		count = awardDao.queryAwardCount(award);
		assertEquals(2, count);
	}

	@Test
	public void testCQueryAwardByAwardId() throws Exception {
		Award awardCondition = new Award();
		awardCondition.setAwardName("测试");
		List<Award> awardList = awardDao.queryAwardList(awardCondition, 0, 2);
		assertEquals(2, awardList.size());
		Award award = awardDao.queryAwardByAwardId(awardList.get(0)
				.getAwardId());
		/*assertEquals("测试一", award.getAwardName());*/
		System.out.println(award.getAwardName());
	}

	@Test
	public void testDUpdateAward() throws Exception {
		Award award = new Award();
		award.setAwardName("测试一");
		List<Award> awardList = awardDao.queryAwardList(award, 0, 1);
		awardList.get(0).setAwardName("第一个测试奖品");
		int effectedNum = awardDao.updateAward(awardList.get(0));
		assertEquals(1, effectedNum);
		//将修改出来的奖品找出来验证
		Award awards = awardDao.queryAwardByAwardId(awardList.get(0).getAwardId());
		System.out.println(awards.getAwardName());
	}

	@Test
	public void testEDeleteAward() throws Exception {
		Award awardCondition = new Award();
		awardCondition.setAwardName("测试");
		List<Award> awardList = awardDao.queryAwardList(awardCondition, 0, 2);
		assertEquals(2, awardList.size());
		for (Award award : awardList) {
			int effectedNum = awardDao.deleteAward(award.getAwardId(),award.getShopId());
			assertEquals(1, effectedNum);
		}
		
	}
}
