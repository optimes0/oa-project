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

<link rel="stylesheet" href="${ctx }/webjars/bootstrap/3.3.7/dist/css/bootstrap.min.css"/>
<script type="text/javascript" src="${ctx }/webjars/jquery/3.3.1/dist/jquery.slim.min.js"></script>
<script type="text/javascript" src="${ctx }/webjars/bootstrap/3.3.7/dist/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="${ctx }/static/css/fkjava.css">
</head>
<body>
<div class="container-fluid">
	<div class="panel panel-primary">
		  <div class="panel-heading">
		  	<div class="col-md-12 headText">
		  		<div class="col-md-10 col-xs-6">
		  			用户列表
			  	</div>
			  	<div class="col-md-2 col-xs-6">
			  		<a class="btn btn-default" href="${ctx }/identity/user/add" role="button">新增</a>
			  	</div>
		  	</div>
		  </div>
		  <div class="panel-body">
		    <table class="table table-striped table-hover">
			  <thead>
			  	<tr>
			  		<th>姓名</th>
			  		<th>登录名</th>
			  		<th>身份</th>
			  		<th>状态</th>
			  		<th>操作</th>
			  	</tr>
			  </thead>
			  <tbody>
			  	<c:forEach items="${page.content }" var="u">
			  		<tr>
			  			<td>${u.name }</td>
			  			<td>${u.loginName }</td>
			  			<td><c:forEach items="${u.roles }" var="r">
			  				<span class="btn btn-info btn-xs">${r.name }</span>
			  			</c:forEach></td>
			  			<td>${u.status }</td>
			  			<td>
			  			  <a href="${ctx }/identity/user/${u.id}">修改</a>
			  			  <c:choose>
			  			    <c:when test="${u.status eq 'DISABLED' || u.status eq 'EXPIRED'}">
			  			    	<a href="${ctx }/identity/user/active/${u.id}">激活</a>
			  			    </c:when>
			  			    <c:otherwise>
			  			    	<a href="${ctx }/identity/user/disable/${u.id}">禁用</a>
			  			    </c:otherwise>
			  			  </c:choose>
			  			</td>
			  			
			  		</tr>
			  	</c:forEach>
			  </tbody>
			</table>
			<%--前缀随意写要与tag指令的前缀相同，后缀要与文件名相同 --%>
			<fk:page page="${page }"/>
		  </div>
	</div>
</div>
</body>
</html>