<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" scope="application"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><sitemesh:write property="title"/></title>
<link rel="stylesheet" href="${ctx }/webjars/bootstrap/3.3.7/dist/css/bootstrap.min.css"/>
<script type="text/javascript" src="${ctx }/webjars/jquery/3.3.1/dist/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/webjars/bootstrap/3.3.7/dist/js/bootstrap.min.js"></script>

<script type="text/javascript" src="${ctx }/static/js/fkjava.js"></script>
<link rel="stylesheet" href="${ctx }/static/css/layout.css"/>
<%-- 把CSRF的验证码放到HTML头里面保存起来 --%>
	<%-- 使用AJAX的时候，必须要设置请求头，请求头的内容从HTML头里面获取 --%>
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	
<sitemesh:write property="head"/>
</head>
<body>
   <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" 
          		  class="navbar-toggle collapsed" 
          		  data-toggle="collapse" 
          		  data-target="#navbar" 
          		  aria-expanded="false" 
          		  aria-controls="navbar">
            <span class="sr-only">显示或隐藏导航</span>
  			<span class="glyphicon glyphicon-align-justify" style="color: white;"></span>
          </button>
          <button type="button" class="navbar-toggle collapsed" data-toggle="sidebar">
			<span class="sr-only">显示或隐藏菜单</span>
			<span class="glyphicon glyphicon-th-list" style="color: white;"></span>
		  </button>
          <a class="navbar-brand" href="${ctx }/layout">Dark intelligent fantasy</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#">首页</a></li>
            <li><a href="#">设置</a></li>
            <!-- authentication认证的意思，principal首要的意思 -->
            <li><a href="#">${sessionScope['SPRING_SECURITY_CONTEXT'].authentication.principal.name }</a></li>
            <li><a href="#">帮助</a></li>
            <li><a href="#" onclick="$('#logout').submit()">退出</a></li>
          </ul>
          <form id="logout" action="${ctx }/security/do-logout" method="post" style="display: none">
       		 <input type="hidden"
				name="${_csrf.parameterName}"
				value="${_csrf.token}"/>
          </form>
          <form class="navbar-form navbar-right" method="get" action="">
            <input type="text" class="form-control" placeholder="输入关键字,按回车搜索" name="keyword" vale="${param.keyword }">
          </form>
        </div>
      </div>
    </nav>
    
    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar" id="left-sidebar">
      		<%-- 一二级菜单显示的地方 --%>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
  			<sitemesh:write property="body"/>
        </div>
      </div>
    </div>

<script type="text/javascript" src="${ctx }/static/js/layout.js"></script>
<script type="text/javascript">
	var contextPath = "${ctx}";
</script>
</body>
</html>