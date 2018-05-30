package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.o2o.dto.WechatAuthExecution;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.WechatAuth;
import com.imooc.o2o.enums.WechatAuthStateEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatAuthServiceTest {

	@Resource
	private WechatAuthService wechatAuthService;
	
	@Test
	public void testRegister() throws Exception {
		WechatAuth wechatAuth = new WechatAuth();
		PersonInfo info = new  PersonInfo();
		String openId = "rt";
		//给微信帐号设置用户信息，但不设置上用户的ID
		//希望创建微信账号的时候自动创建用户信息
		info.setCreateTime(new Date());
		info.setName("测试一下啊");
		info.setUserType(1);
		wechatAuth.setPersonInfo(info);
		wechatAuth.setOpenId(openId);
		wechatAuth.setCreateTime(new Date());
		WechatAuthExecution we  = wechatAuthService.register(wechatAuth);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(),we.getState());
		//通过openId找到新增的wechatAuth
		wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
		//打印用户名字看看跟预期是否相符
		System.out.println(wechatAuth.getPersonInfo().getName());
	}
}
