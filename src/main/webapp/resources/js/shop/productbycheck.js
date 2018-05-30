$(function(){
	var productName = "";
	getProductSellDailyList();
	getList();
	function getList(){
		var listUrl = "/o2o/shopadmin/listuserproductmapsbyshop?pageIndex=1&pageSize=999&productName="+productName;
		$.getJSON(listUrl,function(data){
			if(data.success){
				var userProductMapList = data.userProductMapList;
				var html = "";
				userProductMapList.map(function(item,index){
					html += "<div class='row row-productbuycheck'>";
					html += "<div class='col-20 productbuycheck-time'>"+item.product.productName+"</div>";
					html += "<div class='col-40'>"+new Date(item.createTime).Format("yyyy-MM-dd hh:mm:ss")+"</div>";
					html += "<div class='col-15'>"+item.user.name+"</div>";
					html += "<div class='col-10'>"+item.point+"</div>";
					html += "<div class='col-15'>"+item.operator.name+"</div>";
					html += "</div>";
				});
				$(".productbuycheck-wrap").append(html);
			}
		});
	}
	
	$("#search").on("change",function(e){
		//依据输入的商品名模糊查询该商品的购买记录
		productName = e.target.value;
		//清空商品销售信息
		$(".productbuycheck-wrap").empty();
		//重新查询商品销售信息
		getList();
	});
	
	function getProductSellDailyList(){
		//获取店铺7天的销量的URL
		var listProductSellDailyUrl = "/o2o/shopadmin/listproductselldailyinfobyshop";
		$.getJSON(listProductSellDailyUrl,function(data){
			if(data.success){
				var myChart = echarts.init(document.getElementById("chart"));
				//生成静态的Echart信息部分
				var option = generateStaticEchartPart();
				//遍历销量统计列表，动态设定echart的值
				option.legend.data = data.legendData;
				option.xAxis = data.xAxis;
				option.series = data.series;
				myChart.setOption(option);
			}
		});
	}
	/** echarts逻辑部分 ***/
	/*var myChart = echarts.init(document.getElementById("chart"));
	myChart.setOption(generateStaticEchartPart());*/
	
	function generateStaticEchartPart(){
		var option = {
				tooltip:{
					trigger:"axis",
					axisPointer:{//坐标轴指示器，坐标轴触发有效
						type:"shadow"//鼠标移动至轴上时，出现阴影
					}
				},
				//图例，每个图例最多仅有一个图例
				legend:{
					//图例内容数组，数组项通常为{String}，每一项代表一个系列的name
					data:[/*"茉香奶茶","绿茶奶茶","冰雪奇缘"*/]
				},
				//直角坐标内绘制图网格
				grid:{
					left:"3%",
					right:"4%",
					bottom:"3%",
					containLabel:true
				},
				//直角坐标系中横轴数组，数组中每一项代表一条横轴坐标轴
				xAxis:[{
					//类目型：需要指定类目列表，坐标轴内有且仅有这些指定类目坐标
					type:"category",
					data:[/*"周一","周二","周三","周四","周五","周六","周日"*/]
				}],
				yAxis:[{
					type:"value"
				}],
				//直角坐标系中纵坐标轴数组，数组中每一项代表一条纵轴坐标轴
				series:[/*{
					name:"茉香奶茶",
					type:"bar",
					data:[120,123,124,100,150,200,90]
				},{
					name:"绿茶奶茶",
					type:"bar",
					data:[100,156,135,50,130,150,120]
				},{
					name:"冰雪奇缘",
					type:"bar",
					data:[159,100,180,142,100,120,100]
				}*/]
		};
		return option;
	}
});