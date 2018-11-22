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
					+ "' title='添加部门' onfocus='this.blur();'></span>";
	//把自定义按钮放到span后面
	sobj.after(addStr);
	//给按钮绑定事件
	var btn = $("#addBtn_" + treeNode.tId);
	if(btn)btn.bind("click",function(){
			added = true;
			//删除自定义按钮
			removeHoverDom(treeId,treeNode);
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			var nodes = zTree.addNodes(treeNode,{name:"新部门"});
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
	//把值赋给表单对应的属性
	$("#hr-form [name='id']").val(id);
	$("#hr-form [name='name']").val(name);
	
	if(node.manager && node.manager.user){
		var userId = node.manager.user.id;
		$("#select-manager").val(userId);
	}
	
	//获取上级菜单
	var parent = node.getParentNode();
	if(parent){
		var parentId = parent.id;
		var parentName = parent.name;
		
		$("#hr-form #parentId").val(parentId);
		$("#hr-form #parentName").text(parentName);
	}else{
		$("#hr-form #parentId").val("");
		$("#hr-form #parentName").text("最上级");
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
		url: "${ctx}/human-resources/department/move",
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
		url: "${ctx}/human-resources/department/" + treeNode.id,
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
	$("#hr-form [name='id']").val("");
	$("#hr-form [name='name']").val("");
	
	$("#select-manager").val("");
	
	$("#hr-form #parentId").val("");
	$("#hr-form #parentName").text("");
	
}

var setting = {
		async: {
			//激活异步请求
			enable: true,
			//异步请求的url，默认为post请求
			url: "/human-resources/department",
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
	
	// 处理部门经理选择的自动完成
	$('#selectManager').autocomplete({
		serviceUrl : contextPath + '/identity/user',
		dataType : "json",// 返回JSON
		onSelect : function(suggestion) {
			// 当选中某个选项的时候要执行的回调，需要把用户的ID存储到表单里面
			$("#managerUserId").val( suggestion.user.id );
		}
	});
});