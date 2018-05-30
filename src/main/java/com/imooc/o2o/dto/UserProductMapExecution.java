package com.imooc.o2o.dto;

import java.util.List;

import com.imooc.o2o.entity.UserProductMap;
import com.imooc.o2o.enums.AreaStateEnum;
import com.imooc.o2o.enums.UserProductMapStateEnum;

public class UserProductMapExecution {
	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 店铺数量
	private int count;

	// 操作的UserProductMap（增删改商品的时候用）
	private UserProductMap userProductMap;

	// 获取的award列表(查询商品列表的时候用)
	private List<UserProductMap> userProductMapList;

	public UserProductMapExecution() {
	}

	// 失败的构造器
	public UserProductMapExecution(UserProductMapStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 成功的构造器
	public UserProductMapExecution(UserProductMapStateEnum stateEnum, UserProductMap userProductMap) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userProductMap = userProductMap;
	}

	// 成功的构造器
	public UserProductMapExecution(AreaStateEnum stateEnum, List<UserProductMap> userProductMapList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.userProductMapList = userProductMapList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public UserProductMap getUserProductMap() {
		return userProductMap;
	}

	public void setUserProductMap(UserProductMap userProductMap) {
		this.userProductMap = userProductMap;
	}

	public List<UserProductMap> getUserProductMapList() {
		return userProductMapList;
	}

	public void setUserProductMapList(List<UserProductMap> userProductMapList) {
		this.userProductMapList = userProductMapList;
	}
}
