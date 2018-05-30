$(function(){
	
	//列出该店铺所有授权信息的URL
	var listUrl='/o2o/shopadmin/listshopauthmapsbyshop?pageIndex=1&pageSize=100';
	
	//修改
	var modifyUrl ='/o2o/shopadmin/modifyshopauthmap';
	getList();
	
	function getList(){
		$.getJSON(listUrl,function(data){
			if(data.success){
				var shopauthList=data.shopAuthMapList;
				var tempHtml ='';
				shopauthList.map(function(item,index){
					var textOp="恢复";
					var contraryStatus=0;
					if(item.enableStatus==1){
						//若状态值为1，表明是授权生效，操作变为删除
						textOp="删除";
						contraryStatus==0;
					}else{
						contraryStatus=1;
					}
					tempHtml+=''+'<div class="row row-shopauth">'
					+'<div class="col-40">'+item.employee.name
					+'</div>';
					if(item.titleFlag!=0){
						//若不是店家本人的授权信息则加入编辑以及改变状态等操作
						tempHtml+='<div class="col-20">'+item.title
						+'</div>'+'<div class="col-40">'
						+'<a href="#" class="edit" data-employee-id="'
						+item.employee.userId+'" data-auth-id="'
						+item.shopAuthId+'">编辑</a>'
						+'<a href ="#" class="status" data-auth-id="'
						+item.shopAuthId+'" data-status="'
						+contraryStatus +'">'+textOp+'</a>'
						+'</div>';
						
					}else{
						//若为店家且不允许操作
						tempHtml +='<div class="col-20">'+item.title
						+'</div>'+'<div class ="col-40">'
						+'<span>不可操作</span>'+'</div>';
						
					}
					tempHtml+='</div>';
				});
				$('.shopauth-wrap').html(tempHtml);
			}
		});
	}
	
	/**
	 * 给a标签的click事件绑定对应的方法
	 */
	$('.shopauth-wrap').on('click','a',function(e){
		var target =$(e.currentTarget);
		if(target.hasClass('edit')){
			window.location.href='/o2o/shopadmin/shopauthedit?shopAuthId='
				+e.currentTarget.dataset.authId;
		}else if(target.hasClass('status')){
			changeStatus(e.currentTarget.dataset.authId,
					e.currentTarget.dataset.status);
		}
	});
	function changeStatus(id,status){
		var shopAuth={};
		shopAuth.shopAuthId=id;
		shopAuth.enableStatus=status;
		$.confirm('确定吗？',function(){
			$.ajax({
				url:modifyUrl,
				type:'POST',
				data:{
					//将json参数转换为字符串
					shopAuthMapStr:JSON.stringify(shopAuth),
					statusChange:true,
				},
				dataType:'json',
				success:function(data){
					if(data.success){
						$.toast('操作成功！');
						getList();
					}else{
						$.toast('操作失败！');
					}
				}
			});
		});
	}
	
});