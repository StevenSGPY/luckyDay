$(function() {
    var userName = '';
    function getList() {
        var listUrl = '/o2o/shopadmin/listusershopmapsbyshop?pageIndex=1&pageSize=9999&userName=' + userName;
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var userShopMapList = data.userShopMapList;
                var tempHtml = '';
                userShopMapList.map(function (item, index) {
                    tempHtml += ''
                         +      '<div class="row row-usershopcheck">'
                         +          '<div class="col-50">'+ item.user.name +'</div>'
                         +          '<div class="col-50">'+ item.point +'</div>'
                         +      '</div>';
                });
                $('.usershopcheck-wrap').html(tempHtml);
            }
        });
    }

    //搜索绑定，获取并按照用户名模糊查询
    $('#search').on('input', function (e) {
        userName = e.target.value;
        $('.usershopcheck-wrap').empty();
        getList();
    });
    getList();
});