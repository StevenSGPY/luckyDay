$(function() {
	var bindUrl = '/o2o/local/bindlocalauth';

	//从地址栏URl里获取userType
	//userType=1则为前端展示系统，其余为店家管理系统
	var userType = getQueryString('userType');
	$('#submit').click(function() {
		var userName = $('#username').val();
		var password = $('#psw').val();
		var verifyCodeActual = $('#j_captcha').val();
		var needVerify = false;
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		//访问后台，绑定账号
		$.ajax({
			url : bindUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual
			},
			success : function(data) {
				if (data.success) {
					$.toast('绑定成功！');
					setTimeout(function(){
						if (userType == 1) {
							// 若用户在前端展示系统页面则自动退回到前端展示系统首页
							window.location.href = '/o2o/frontend/index';
						} else {
							// 若用户在店家管理系统，则自动回退到店铺列表首页
							window.location.href = '/o2o/shopadmin/shoplist';
						}
					},1100)
				} else {
					$.toast('绑定失败！'+data.errMsg);
					$('#captcha_img').click();
				}
			}
		});
	});
});