package com.imooc.o2o.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.o2o.entity.Area;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaServiceTest{
	@Resource
	private AreaService areaService;
	@Resource
	private CacheService cacheService;
	
	@Test
	public void testGetAreaList() {
		List<Area> arealist = areaService.getAreaList();
		for (Area area : arealist) {
			System.out.println(area.getAreaName());
		}
		cacheService.removeFromCache(areaService.AREALISTKEY);
		arealist = areaService.getAreaList();
	}
	
	
	
}
