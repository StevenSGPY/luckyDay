$(function() {
	var userAwardId = getQueryString("userAwardId");

	var detailUrl = "/o2o/frontend/getawardbyuserawardid";

	var awardId = getQueryString("awardId");

	var awardDetailUrl = "/o2o/frontend/getawardbyawardid";

	if (userAwardId != null && userAwardId != undefined && userAwardId != "") {
		getUserAwardMap();
	}
	if (awardId != null && awardId != undefined && awardId != "") {
		getAward();
	}
	

	function getUserAwardMap() {
		var url = detailUrl + "?userAwardId=" + userAwardId;
		$
				.getJSON(url,function(data) {
							if (data.success) {
								var award = data.award;
								$("#award-img").attr("src",
										getContextPath() + award.awardImg);
								$("#award-time").text(
										new Date(award.createTime)
												.Format("yyyy-MM-dd"));
								$("#award-name").text(award.awardName);
								$("#award-desc").text(award.awardDesc);
								if (data.usedStatus == 0) {
									var imgListHtml = '<div> <img src="/o2o/frontend/generateqrcode4award?userAwardId='
											+ userAwardId
											+ '" width="100%"/></div>'
									$("#QRCode").append(imgListHtml);
								}
							}
						});
	}
	
	function getAward(){
		var url = awardDetailUrl + "?awardId="+awardId;
		$.getJSON(url,function(data){
			if(data.success){
				var award = data.award;
				$("#award-img").attr("src",getContextPath()+award.awardImg);
				$("#award-time").text(new Date(award.createTime).Format("yyyy-MM-dd"));
				$("#award-name").text(award.awardName);
				$("#award-desc").text(award.awardDesc);
			}
		});
	}

	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
});