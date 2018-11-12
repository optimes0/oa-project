/**
 * 
 */
$(function(){
		// 显示或者隐藏侧边栏
		$('[data-toggle="sidebar"]').click(function() {
			$('.sidebar').toggleClass('active')
		});
		
		//获取当前用户的菜单并显示出来
		$.ajax({
			url: "/menu/menus",
			method: "GET",
			dataType: "JSON",
			success: function(menus,status,xhr){
				$(menus).each(function(index,menu){
					//使用反引号是ECMAScript 5/6里面提供的新功能，可以直接写多行的字符串
					//$表示引用变量
					//${}只能在js里使用，在jsp使用会产生冲突
					var html = `<div class="sidebar-header">${menu.name}</div>
						<ul class="nav nav-sidebar">`;
					$.each(menu.children,function(index,child){
						 var item = `<li><a href="${child.url}">${child.name}</a></li>`;
						 
						 html += item;
					})
					
					html += `</ul>`;
					
					if(menu.children.length > 0){
						$(html).appendTo($("#left-sidebar"));
					}
				})
			},
			error: function(data,status,xhr){
				alert(data.responseJSON.message);
			}
		})
	})