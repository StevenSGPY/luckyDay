$(function() {
	// 登录验证的controller url
	var loginUrl = '/o2o/local/logincheck';
	// 从地址栏的URl里获取uertype
	// usertype=1则为customer，其余为shopowner
	var usertype = getQueryString('usertype');
	// 登录次数，累计登陆三次失败后自动弹出验证码要求输入
	var loginCount = 0;

	$('#submit').click(function() {
		// 获取输入的帐号
		var userName = $('#username').val();
		var password = $('#psw').val();
		// 获取验证码信息
		var verifyCodeActual = $('#j_captcha').val();
		// 是否需要验证码的验证，默认为false，即不需要
		var needVerify = false;
		// 如果登陆三次都失败
		if (loginCount >= 3) {
			// 那么就需要验证码校验了
			if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		// 访问后台进行登录验证
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
				userName : userName,
				password : password,
				verifyCodeActual : verifyCodeActual,
				// 是否需要做验证码校验
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					$.toast('登录成功！');
					setTimeout(function(){
						if (usertype == 1) {
							// 若用户在前端展示系统页面则自动退回到前端展示系统首页
							window.location.href = '/o2o/frontend/index';
						} else {
							// 若用户在店家管理系统，则自动回退到店铺列表首页
							window.location.href = '/o2o/shopadmin/shoplist';
						}
					},1100)
				} else {
					$.toast('登录失败！' + data.errMsg);
					loginCount++;
					if (loginCount >= 3) {
						// 登录失败三次，需要做验证码校验
						$('#verifyPart').show();
					}
				}

			}
		})

	});

});