<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>阅读公告</title>
<link rel="stylesheet" href="${ctx }/static/css/fkjava.css">
</head>
<body>
<div class="container-fluid">
	<div class="panel panel-primary">
		<div class="panel-heading">
			阅读公告
		</div>
		<div class="panel-body">
			<h1 class="text-center">${notice.title }</h1>
			<div class="text-center">
				作者：${notice.author.name }   发布时间：<fmt:formatDate value="${notice.releaseTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
			<hr/>
			${notice.content }
			<hr/>
		</div>
		<div class="panel-foot col-xs-offset-9">
			<c:if test="${empty noticeRead.readTime }">
				<a class="btn btn-primary col-xs-offset-3" onclick="readNotice('${notice.id}')" style="width: 100px;">已阅</a>
			</c:if>
			<c:if test="${not empty noticeRead.readTime }">
				<h4>已阅读：<fmt:formatDate value="${noticeRead.readTime }" pattern="yyyy-MM-dd HH:mm:ss"/></h4>
			</c:if>
		</div>
	</div>
</div>
<script type="text/javascript">
	function readNotice(id){
		$.ajax({
			url: "${ctx}/notice/readed/"+id,
			method: "post",
			success: function(){
				window.location.href="${ctx}/notice";
			}
		})
	}
</script>
</body>
</html>