package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin", method = { RequestMethod.GET })
public class ShopAdminController {
	@RequestMapping(value = "/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}

	@RequestMapping(value = "/shoplist")
	public String shopList() {
		return "shop/shoplist";
	}

	@RequestMapping(value = "/shopmanagement")
	public String shopManagement() {
		return "shop/shopmanagement";
	}
	
	@RequestMapping(value = "/productcategorymanage",method=RequestMethod.GET)
	public String productCategoryManage() {
		return "shop/productcategorymanage";
	}
	
	@RequestMapping(value = "/productoperation",method=RequestMethod.GET)
	public String productOperation() {
		return "shop/productoperation";
	}
	
	@RequestMapping(value = "/productmanage",method=RequestMethod.GET)
	public String productManage() {
		return "shop/productmanage";
	}
	@RequestMapping(value = "/shopauthmanagement",method=RequestMethod.GET)
	public String shopAuthManagement() {
		//转发至店铺授权页面
		return "shop/shopauthmanagement";
	}
	
	@RequestMapping(value = "/shopauthedit",method=RequestMethod.GET)
	public String shopAuthEdit() {
		//转发至店铺授权编辑页面
		return "shop/shopauthedit";
	}
	
	@RequestMapping(value = "/productbuycheck",method=RequestMethod.GET)
	public String productBuyCheck() {
		//转发至店铺的消费记录页面
		return "shop/productbuycheck";
	}
	
	@RequestMapping(value="/usershopcheck", method=RequestMethod.GET)
	public String userShopCheck() {
		//店铺用户积分统计路由
		return "shop/usershopcheck";
	}
	
	@RequestMapping(value="/awarddelivercheck", method=RequestMethod.GET)
	public String awardDeliverCheck() {
		//店铺用户积分兑换路由
		return "shop/awarddelivercheck";
	}
	
	@RequestMapping(value = "/awardmanagement",method=RequestMethod.GET)
	public String awardManagement() {
		//奖品管理路由
		return "shop/awardmanagement";
	}
	@RequestMapping(value="/awardoperation",method=RequestMethod.GET)
	public String awardOperation() {
		//奖品编辑页面路由
		return "shop/awardoperation";
	}
	
	
	
	
	
	

}
