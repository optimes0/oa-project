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

<link rel="stylesheet" href="${ctx }/static/css/layout.css"/>
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
            <li><a href="#">个人</a></li>
            <li><a href="#">帮助</a></li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
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