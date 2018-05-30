package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.UserProductMap;

public interface UserProductMapDao {
	/**
	 * 依据传入进来的查询条件分页显示返回用户购买商品的记录列表
	 * @param userProductCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<UserProductMap> queryUserProductMapList(
			@Param("userProductCondition") UserProductMap userProductCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

	/**
	 * 配合queryUserProductMapList根据相同的查询条件返回用户购买商品数
	 * @param userProductCondition
	 * @return
	 */
	int queryUserProductMapCount(
			@Param("userProductCondition") UserProductMap userProductCondition);

	/**
	 * 增加一条用户购买商品的记录
	 * @param userProductMap
	 * @return
	 */
	int insertUserProductMap(UserProductMap userProductMap);
}
