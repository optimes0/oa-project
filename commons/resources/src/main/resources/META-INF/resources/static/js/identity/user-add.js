/**
 * 
 */
$(function(){
		//添加所选
		$(".add-select").click(function(){
			//找到勾选了的未选中角色
			$(".unselect-roles ul li input:checked").each(function(index,input){
				//取消选中
				$(input).prop("checked",false);
				//找到input的li
				var li = $(input).parent().parent();
				$(".select-roles ul").append(li);
			})
		})
		//添加全部
		$(".add-all").click(function(){
			//找到所有角色
			$(".unselect-roles ul li input").each(function(index,input){
				//取消选中
				$(input).prop("checked",false);
				//添加进选中的一边
				var li = $(input).parent().parent();
				$(".select-roles ul").append(li);
			})
		})
		//移除全部
		$(".remove-all").click(function(){
			//找到所有角色
			$(".select-roles ul li input").each(function(index,input){
				//取消选中
				$(input).prop("checked",false);
				//添加进未选中的一边
				var li = $(input).parent().parent();
				$(".unselect-roles ul").append(li);
			})
		})
		//移除所选
		$(".remove-select").click(function(){
			//找到选中的角色
			$(".select-roles ul li input:checked").each(function(index,input){
				//取消选中
				$(input).prop("checked",false);
				//添加进未选中的一边
				var li = $(input).parent().parent();
				$(".unselect-roles ul").append(li);
			})
		})
		
		//在提交表单的时候，把选择的角色全部勾选
		//未选择的角色全部取消勾选
		//把勾选的input中的name属性的数字，全部替换成索引
		$("form").bind("submit",function(){
			$(".select-roles ul li input").each(function(index,input){
				//全部选中
				$(input).prop("checked",true);
				//替换name中的名字
				var name = $(input).attr("name");
				name = name.replace(/\d+/,index);
				$(input).attr("name",name);
			})
			
			$(".unselect-roles ul li input").each(function(index,input){
				//全部取消勾选
				$(input).prop("checked",false);
			})
		})
	})