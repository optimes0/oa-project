<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公告类型</title>
<link rel="stylesheet" href="${ctx }/static/css/identity/role-index.css">
</head>
<body>
<div class="container-fluid">
	<div class="panel panel-primary ">
		  <div class="panel-heading">
		  	<div class="col-md-12 headText">
		  		<div class="col-md-10 col-xs-6">
		  			公告类型管理
			  	</div>
		  	</div>
		  </div>
		  <div class="panel-body" id="body">
		  	<div class="col-sm-12 col-md-4">
		  		<c:forEach items="${types }" var="type">
		  		  <div class="types row"
		  		  		data-id="${type.id }"
		  		  		data-name="${type.name }">
		  			<div class="col-md-8">${type.name }</div>
		  			<input type="button" style="display: none;" 
		  				class="btn btn-danger btn-xs col-md-offset-2" value="删除"/>
		  		  </div>
		  		</c:forEach>
		  	</div>
		  	<div class="col-sm-12 col-md-8 type-form">
		  		<!-- 类型修改的表单 -->
		  		<form class="col-xs-12 col-md-offset-3" style="width: 50%" 
						action="" method="post">
				  <input type="hidden" name="id">
				  <div class="form-group">
				    <label for="exampleInputEmail1">公告类型</label>
				    <input type="text" class="form-control" 
				    		name="name" placeholder="请输入公告类型"
				    		required="required" >
				  </div>
				  <input type="hidden"
						name="${_csrf.parameterName}"
						value="${_csrf.token}"/>
				  <button id="submit" class="btn btn-default">新增</button>
				  <button id="alter" style="display: none;" class="btn btn-default">修改</button>
				</form>
				  
		  	</div>
		    
		  </div>
	</div>
  </div>
<script type="text/javascript">
$(function(){
	$(".types").click(function(){
		//发生事件的div
		var div = $(this);
		var id = div.attr("data-id");
		var name = div.attr("data-name");
		
		$(".type-form [name='id']").val(id);
		$(".type-form [name='name']").val(name);
		
		$(".types").removeClass("role-select");
		
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
		//发送delete请求删除数据
		$.ajax({
			url:"${ctx}/notice/type/"+id,
			method:"DELETE",//发送delete请求
			success:function(data,item){
				//刷新
				window.location.reload();
			},
			error:function(data){
				//responseJSON表示返回的对象
				//message表示错误信息
				alert(data.responseJSON.message);
			}
		})
	})
	
	$("#submit").click(function(event){
		$(".type-form [name='id']").val("");
		
	})
	
})
</script>
</body>
</html>