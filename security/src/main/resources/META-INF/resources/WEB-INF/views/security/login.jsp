<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户登录</title>
</head>
<body>
	<div class="col-sm-12 col-md-6 col-md-offset-4 text-center">
		<form class="form-signin" style="width: 60%" 
				action="/security/do" method="post"/>
		    <h2 class="form-signin-heading">请登录</h2>
		    <label for="inputLoginName" class="sr-only">登录名</label>
		    <input id="inputLoginName" 
		    	class="form-control" 
		    	placeholder="登录名" 
		    	required="required" 
		    	autofocus="autofocus"
		    	name="loginName"/>
		    <label for="inputPassword" class="sr-only">密码</label>
		    <input type="password" 
		    	id="inputPassword" 
		    	class="form-control" 
		    	placeholder="登录密码" 
		    	required="required"
		    	name="password">
		    <input type="hidden"
				name="${_csrf.parameterName}"
				value="${_csrf.token}"/>
		    <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
		</form>
	</div>
</body>
</html>