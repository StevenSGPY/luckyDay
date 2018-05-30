$(function() {
	var productId = getQueryString('productId');
	var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId='
			+ productId;
	$
			.getJSON(
					productUrl,
					function(data) {
						if (data.success) {
							var product = data.product;
							// 给商品信息相关HTML控件赋值
							// 商品缩略图
							$('#product-img').attr('src',
									getContextPath() + product.imgAddr);
							// 更新时间
							$('#product-time').text(
									new Date(product.lastEditTime)
											.Format("yyyy-MM-dd"));
							if (product.point != undefined) {
								$('#product-point').text(
										'购买可得' + product.point + '积分');
							}
							$('#product-name').text(product.productName);
							$('#product-desc').text(product.productDesc);

							// 商品价格展示逻辑，主要判断原价现价是否为空，所有都为空则不显示价格栏目
							if (product.normalPrice != undefined
									&& product.promotionPrice != undefined) {
								$('#price').show();
								$('#normalPrice').html(
										'<del> ￥' + product.normalPrice
												+ '</del>');
								$('#promotionPrice').html(
										'￥' + product.promotionPrice);
							} else if (product.normalPrice != undefined
									&& product.promotionPrice == undefined) {
								$('#price').show();
								$('#promotionPrice').html(
										'￥' + product.normalPrice);
							} else if (product.normalPrice == undefined
									&& product.promotionPrice != undefined) {
								$('#promotionPrice').html(
										'￥' + product.promotionPrice);
							}
							var imgListHtml = '';
							product.productImgList.map(function(item, index) {
								imgListHtml += '<div> <img src="'
										+ getContextPath() + item.imgAddr
										+ '" width="100%" /></div>';
							});

							if (data.needQRCode) { 
								//若顾客已经登录，生成购买商品的二维码供商家扫描
								imgListHtml += '<div><img src="/o2o/frontend/generateqrcode4product?productId='
										+ product.productId
										+ '" width="100%"/></div>';
							}
							$('#imgList').html(imgListHtml);
						}
					});
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
