/**
 * 
 */
var added = false;//限制只能每次添加一个菜单
var addHoverDom = function(treeId,treeNode){
	//找到节点的span
	var sobj = $("#"+treeNode.tId+"_span");
	if(added//如果添加了一个子菜单，不再显示添加按钮
			|| treeNode.editNameFlag//判断是否正在编辑名字
			|| $("#addBtn_"+treeNode.tId).length > 0//判断是否有添加按钮
			){
		//在编辑名字，有添加按钮都不需要再增加新的按钮
		return;
	}
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
					+ "' title='添加菜单' onfocus='this.blur();'></span>";
	//把自定义按钮放到span后面
	sobj.after(addStr);
	//给按钮绑定事件
	var btn = $("#addBtn_" + treeNode.tId);
	if(btn)btn.bind("click",function(){
			added = true;
			//删除自定义按钮
			removeHoverDom(treeId,treeNode);
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			var nodes = zTree.addNodes(treeNode,{name:"新菜单"});
			//选中节点，因为只是添加一个，所以node里面只有一个
			zTree.selectNode(nodes[0],false,true);
			return false;
		});
}
//使用var定义的变量，在运行的时候会放到最上面，但不会赋值
//使用function定义会在所有代码运行前先在内存分配空间
function removeHoverDom(treeId,treeNode){
	$("#addBtn_"+treeNode.tId).unbind().remove();
}

function showToForm(treeId,node){
	//在选中前先清空
	resetForm();
	//获取选中菜单的属性值
	var id = node.id;
	var name = node.name;
	var url = node.url;
	var type = node.type;
	var roles = node.roles;
	//把值赋给表单对应的属性
	$("#menu-form [name='id']").val(id);
	$("#menu-form [name='name']").val(name);
	$("#menu-form [name='url']").val(url);
	$("#menu-form input[name='type'][value='"+type+"']").prop("checked",true);
	//获取上级菜单
	var parent = node.getParentNode();
	if(parent){
		var parentId = parent.id;
		var parentName = parent.name;
		
		$("#menu-form #parentId").val(parentId);
		$("#menu-form #parentName").text(parentName);
	}else{
		$("#menu-form #parentId").val("");
		$("#menu-form #parentName").text("最上级");
	}
	
	if(roles){
		$(".remove-all").click()
		
		$(".unselect-roles ul li input").prop("checked",false);
		$.each(roles,function(index,item){
			$(".unselect-roles ul li input[value='"+item.id+"']").prop("checked",true);
		})
		
		$(".add-select").click();
	}
}

function dropNode(event,treeId,treeNodes,targetNode,moveType){
	var requestData = new Object();
	//要移动节点的id
	requestData.id = treeNodes[0].id;
	if(!requestData.id){
		//如果没有移动节点的id则直接返回，不需要到服务器移动
		return;
	}
	//要移动到哪个目标位置
	if(targetNode){
		requestData.targetId = targetNode.id;
	}else{
		requestData.targetId = "";
	}
	//移动类型，有inner,prev,next
	requestData.moveType = moveType;
	
	$.ajax({
		url: "../menu/move",
		method: "POST",
		dataType: "json",
		data: requestData,
		success: function(data,status,xhr){
			
		},
		error: function(data,status,xhr){
			alert(data.responseJSON.message);
		}
	})
}

//显示删除菜单
function showRemoveBtn(treeId, treeNode){
	//没有下级菜单可删除
	return treeNode.children == 0;
}

//执行删除操作
function removeNode(treeId, treeNode){
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	//false表示不要触发回调
	zTree.removeNode(treeNode, false);
}

function beforeRemoveNode(treeId, treeNode){
	//无论如何返回false，当把服务器里的数据删除成功后再手动删除节点
	$.ajax({
		url: "../menu/" + treeNode.id,
		method: "DELETE",
		dataType: "json",
		success: function(data, status, xhr){
			//两个等号会进行类型转换，三个等号不会
			if(data.code === 1){
				//删除成功，删除节点
				removeNode(treeId, treeNode);
			}
		},
		error: function(data, status, xhr){
			alert(data.responseJSON.message);
		}
		
	})
}

function resetForm(){
	$("#menu-form [name='id']").val("");
	$("#menu-form [name='name']").val("");
	$("#menu-form [name='url']").val("");
	$("#menu-form input[name='type']").prop("checked",false);
	
	$("#menu-form #parentId").val("");
	$("#menu-form #parentName").text("");
	
	$(".remove-all").click();
}

var setting = {
		async: {
			//激活异步请求
			enable: true,
			//异步请求的url，默认为post请求
			url: "../menu",
			//以get方式请求
			type: "GET",
			//返回的数据类型，要求是json
			dataType: "JSON"
		},
		view: {
			//当鼠标移动在节点上的时候，增加自定义按钮
			addHoverDom: addHoverDom,
			//当鼠标离开节点的时候，移除自定义按钮
			removeHoverDom: removeHoverDom,
			//禁止多选
			selectedMulti: false
		},
		edit: {
			enable: true,
			//禁止在菜单树上直接修改名字，而是点击以后在表单里修改
			showRenameBtn: false,
			drag: {
				//禁止复制
				isCopy: false,
				//允许拖动
				isMove: true
			},
			showRemoveBtn: showRemoveBtn
		},
		callback: {
			onSelected: showToForm,
			onDrop: dropNode,
			beforeRemove: beforeRemoveNode
		}
};



$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting);
	
	$(".reset-button").click(resetForm);
});


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