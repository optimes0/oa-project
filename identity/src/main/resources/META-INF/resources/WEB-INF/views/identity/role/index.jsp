<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--引用tag文件 --%>
<%@taglib prefix="fk" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" scope="application"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户界面</title>


<link rel="stylesheet" href="${ctx }/static/css/fkjava.css">
<link rel="stylesheet" href="${ctx }/static/css/identity/role-index.css">
<script type="text/javascript" src="${ctx }/static/js/identity/role-index.js"></script>
</head>
<body>
<div class="container-fluid">
  
	<div class="panel panel-primary ">
		  <div class="panel-heading">
		  	<div class="col-md-12 headText">
		  		<div class="col-md-10 col-xs-6">
		  			角色管理
			  	</div>
			  	<div class="col-md-2 col-xs-6">
			  		<a class="btn btn-default" href="${ctx }/identity/user" role="button">用户管理</a>
			  	</div>
		  	</div>
		  </div>
		  <div class="panel-body" id="body">
		  	<div class="col-sm-12 col-md-4">
		  		<c:forEach items="${roles }" var="role">
		  			<div class="role row"
		  				data-id="${role.id }"
		  				data-name="${role.name }"
		  				data-roleKey="${role.roleKey }">
		  				<div class="col-md-8">
		  				${role.name }(${role.roleKey })
		  				</div>
		  				<input type="button" style="display: none;" 
		  				class="btn btn-danger btn-xs col-md-offset-2" value="删除"/>
		  			</div>
		  		</c:forEach>
		  	</div>
		  	<div class="col-sm-12 col-md-8 role-form">
		  		<!-- 角色修改的表单 -->
		  		<form class="col-xs-12 col-md-offset-3" style="width: 50%" 
						action="" method="post">
				  <input type="hidden" name="id">
				  <div class="form-group">
				    <label for="exampleInputEmail1">角色名称</label>
				    <input type="text" class="form-control" 
				    		name="name" placeholder="请输入角色名称"
				    		required="required" >
				  </div>
				  <div class="form-group">
				    <label for="exampleInputPassword1">角色KEY</label>
				    <input type="text" class="form-control" 
				    		name="roleKey" placeholder="请输入角色KEY"
				    		required="required">
				  </div>
				  
				  <button id="submit" class="btn btn-default">新增</button>
				  <button id="alter" style="display: none;" class="btn btn-default">修改</button>
				</form>
				  
		  	</div>
		    
		  </div>
	</div>
  </div>

</body>
</html>