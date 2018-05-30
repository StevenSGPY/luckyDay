$(function() {
	//从URL里获取awardId参数的值
	var awardId = getQueryString('awardId');
	var infoUrl = '/o2o/shopadmin/getawardbyid?awardId=' + awardId;
	var awardPostUrl = '/o2o/shopadmin/modifyaward';
	//由于奖品添加和编辑使用的是同一页面，
	//该标识用来标明本次是添加还是编辑操作
	var isEdit = false;
	if (awardId) {
		//若有awardId则为编辑操作
		getInfo(awardId);
		isEdit = true;
	} else {
		awardPostUrl = '/o2o/shopadmin/addaward';
	}

	function getInfo(id) {
		$.getJSON(infoUrl, function(data) {
			if (data.success) {
				var award = data.award;
				$('#award-name').val(award.awardName);
				$('#priority').val(award.priority);
				$('#award-desc').val(award.awardDesc);
				$('#point').val(award.point);
			}
		});
	}

	//分别对奖品添加和编辑操作做不同的响应
	$('#submit').click(function() {
		var award = {};
		award.awardName = $('#award-name').val();
		award.priority = $('#priority').val();
		award.awardDesc = $('#award-desc').val();
		award.point = $('#point').val();
		award.awardId = awardId ? awardId : '';
		//获取缩略图文件流
		var thumbnail = $('#small-img')[0].files[0];
		var formData = new FormData();
		formData.append('thumbnail', thumbnail);
		//将award json对象转成字符流保存至表单对象key为awardStr的键值对里
		formData.append('awardStr', JSON.stringify(award));
		//获取表单里输入的验证码
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		//将数据提交后台
		$.ajax({
			url : awardPostUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					$('#captcha_img').click();
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});
});