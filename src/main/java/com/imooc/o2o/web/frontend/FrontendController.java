package com.imooc.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontendController {
	
	@RequestMapping(value = "/mainpage", method = RequestMethod.GET)
	private String showMainPage() {
		return "frontend/mainpage";
	}

	/**
	 * 商品详情路由
	 * @return
	 */
	@RequestMapping(value = "/productdetail", method = RequestMethod.GET)
	private String showProductDetail() {
		return "frontend/productdetail";
	}
	
	/**
	 * 店铺详情页路由
	 * @return
	 */
	@RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
	private String showShopDetail() {
		return "frontend/shopdetail";
	}

	/**
	 * 店铺列表路由
	 * @return
	 */
	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
	private String showShopList() {
		return "frontend/shoplist";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	private String index() {
		return "frontend/index";
	}

	@RequestMapping(value = "/mypoint", method = RequestMethod.GET)
	private String myPoint() {
		return "frontend/mypoint";
	}

	@RequestMapping(value = "/myrecord", method = RequestMethod.GET)
	private String myRecord() {
		return "frontend/myrecord";
	}

	@RequestMapping(value = "/pointrecord", method = RequestMethod.GET)
	private String pointRecord() {
		return "frontend/pointrecord";
	}

	/**
	 * 奖品详情路由
	 * @return
	 */
	@RequestMapping(value = "/myawarddetail", method = RequestMethod.GET)
	private String awardDetail() {
		return "frontend/myawarddetail";
	}
	
	@RequestMapping(value = "/awardlist", method = RequestMethod.GET)
	private String awardList() {
		return "frontend/awardlist";
	}

}
