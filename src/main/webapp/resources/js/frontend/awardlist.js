$(function(){
	var loading = false;
	var maxItems = 999;
	var pageSize = 4;
	var listUrl = "/o2o/frontend/listawardsbyshop";
	var exchangeUrl = "/o2o/frontend/adduserawardmap";
	var pageNum = 1;
	var shopId = getQueryString("shopId");
	var awardName = "";
	var canProceed = false;
	var totalPoint = 0;
	
	addItems(pageSize,pageNum);
	
	function addItems(pageSize,pageIndex){
		var url = listUrl + "?pageIndex="+pageIndex+"&pageSize="+pageSize+"&shopId="+shopId+"&awardName="+awardName;
		loading=true;
		$.getJSON(url,function(data){
			if(data.success){
				var html = "";
				maxItems = data.count;
				data.awardList.map(function(item,index){
					html += '' + '<div class="card" data-award-id="'
					+ item.awardId + '" data-point="'+item.point+'">' + '<div class="card-header">'
					+ item.awardName + '<span class="pull-reight">需要消耗'+item.point+'</span></div>'
					+ '<div class="card-content">'
					+ '<div class="list-block media-list">' + '<ul>'
					+ '<li class="item-content">'
					+ '<div class="item-media">' + '<img src="'
					+ getContextPath() + item.awardImg + '" width="44">' + '</div>'
					+ '<div class="item-inner">'
					+ '<div class="item-subtitle">' + item.awardDesc
					+ '</div>' + '</div>' + '</li>' + '</ul>'
					+ '</div>' + '</div>' + '<div class="card-footer">'
					+ '<p class="color-gray">'
					+ new Date(item.createTime).Format("yyyy-MM-dd")
					+ '更新</p>';
					if(data.totalPoint != undefined){
						//若用户在该店铺有积分，则显示领取按钮
						html +='<span>点击领取</span></div><div>'
					}else{
						html += '</div></div>'
					}
				});
				$(".list-div").append(html);
				if(data.totalPoint != undefined){
					//若用户在该店铺有积分，则显示
					canProceed =true;
					$(".title").text("当前积分"+data.totalPoint);
					totalPoint = data.totalPoint;
				}
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					// 删除加载提示符
					$('.infinite-scroll-preloader').hide();
				}else{
					$('.infinite-scroll-preloader').show();
					loading = false;
				}
				pageNum += 1;
				
				$.refreshScroller();
			}
		});
	}
	
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});
	
	$(".list-div").on("click",".card",function(e){
		var currentPoint = parseInt(e.currentTarget.dataset.point);
		var awardId = e.currentTarget.dataset.awardId;
		if(canProceed && (totalPoint >= currentPoint)){
			$.confirm("需要消耗"+currentPoint+"积分，确认操作么？",function(){
				$.ajax({
					url:exchangeUrl,
					type:'post',
					dataType:'json',
					data:{awardId:awardId,shopId:shopId,point:currentPoint},
					success:function(data){
						if(data.success){
							$.toast("操作成功！");
							totalPoint = totalPoint - currentPoint;
							$(".title").text("当前积分"+totalPoint);
						}else{
							$.toast("操作失败！");
						}
					}
				});
			});
		}else{
			$.toast("积分不足，无权操作");
		}
	});
	
	$('#search').on('input', function(e) {
		awardName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
	
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
});