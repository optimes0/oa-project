$(function(){
		$(".role").click(function(){
			//发生事件的div
			var div = $(this);
			var id = div.attr("data-id");
			var name = div.attr("data-name");
			var roleKey = div.attr("data-roleKey");
			
			$(".role-form [name='id']").val(id);
			$(".role-form [name='name']").val(name);
			$(".role-form [name='roleKey']").val(roleKey);
			
			$(".role").removeClass("role-select");
			
			$(this).addClass("role-select");
			$("#alter").show();
		}).hover(function(){
			$(this).find("input").show();
		},function(){
			$(this).find("input").hide();
		})
		
		$(".btn-danger").click(function(){
			var div = $(this).parent();
			var id = div.attr("data-id");
			var url = "role/"+id;
			//发送delete请求删除数据
			$.ajax({
				url:url,
				method:"DELETE",//发送delete请求
				success:function(data,item){
					//重定向
					window.location = "/identity/role";
				},
				error:function(data){
					//responseJSON表示返回的对象
					//message表示错误信息
					alert(data.responseJSON.message);
				}
			})
		})
		
		$("#submit").click(function(event){
			$(".role-form [name='id']").val("");
			
		})
		
})