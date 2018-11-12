<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" scope="application"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>添加用户</title>

<link rel="stylesheet" href="${ctx }/static/css/identity/user-add.css">
<script type="text/javascript" src="${ctx }/static/js/identity/user-add.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="panel panel-primary">
			<div class="panel-heading">
				添加用户
			</div>
			<div class="panel-body">
				<form class="col-xs-12 col-md-offset-4" style="width: 30%" 
						action="${ctx }/identity/user/add" method="post">
				  <!-- 修改时需要有id -->
				  <input name="id" value="${user.id }" type="hidden">
				  <div class="form-group">
				    <label for="name">姓名</label>
				    <input type="text" class="form-control" 
				    		name="name" placeholder="请输入真实姓名"
				    		required="required" value="${user.name }">
				  </div>
				  <div class="form-group">
				    <label for="loginName">登录名</label>
				    <input type="text" class="form-control" 
				    		name="loginName" placeholder="请输入登录名"
				    		required="required" ${not empty user ? 'readonly="readonly"' : ''}
				    		value="${user.loginName }">
				  </div>
				  <div class="form-group">
				    <label for="password">密码</label>
				    <input type="password" class="form-control" 
				    		name="password" 
				    		${empty user ? 'required="required"' : '' }
				    		placeholder="请输入密码${not empty user ? ',不修改则不写 '  : '' }">
				  </div>
				  <div class="from-group">
				  	<div class="row">
					  	<div class="col-sm-5">
					  	已选择
					  	</div>
					  	<div class="col-sm-2">
					  	</div>
					  	<div class="col-sm-5">
					  	未选择
					  	</div>
				  	</div>
				  	<div class="row">
				  		<div class="col-sm-5 roles select-roles">
				  			<ul>
				  			  <c:forEach items="${user.roles }" var="r">
			  					<c:if test="${not r.fixed }">
		  						  <li>
		  							<label>
		  								<input type="checkbox" name="roles[0].id" value="${r.id }"/>
		  								${r.name }
		  							</label>
		  						  </li>
			  					</c:if>
				  			  </c:forEach>
				  			</ul>
				  		</div>
				  		<div class="col-sm-2 select-button" >
				  			<a class="btn btn-default btn-sm add-select">添加所选</a>
				  			<a class="btn btn-default btn-sm add-all">添加全部</a>
				  			<a class="btn btn-default btn-sm remove-all">移除全部</a>
				  			<a class="btn btn-default btn-sm remove-select">移除所选</a>
				  		</div>
				  		<div class="col-sm-5 roles unselect-roles">
				  			<ul>
				  			  <c:forEach items="${roles }" var="r">
				  			  	<%--因为重写了equals和hashcode，才可以使用contains进行判断r是否在user里面 --%>
				  			    <c:if test="${not user.roles.contains(r) }">
				  				  <li>
				  				    <label>
				  				      <input type="checkbox" name="roles[0].id" value="${r.id }"/>
				  				      ${r.name }
				  				    </label>
				  				  </li>
				  				</c:if>
				  			  </c:forEach>
				  			</ul>
				  		</div>
				  	</div>
				  </div>
				  <button type="submit" class="btn btn-default">${empty user ? '添加' : '修改' }</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>