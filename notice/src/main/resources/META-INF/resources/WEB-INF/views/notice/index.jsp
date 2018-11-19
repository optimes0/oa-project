<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fk" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公告管理 </title>
<link rel="stylesheet" href="${ctx }/static/css/fkjava.css">
<style type="text/css">
.unread{
	font-weight: bold;
}
</style>
</head>
<body>
<div class="container-fluid">
	<div class="panel panel-primary">
		<!-- Default panel contents -->
		<div class="panel-heading">
		  <div class="col-md-12 headText">
			<div class="col-md-10 col-xs-6">
		  			公告管理
			</div>
			<div class="col-md-2 col-xs-6">
				<a class="btn btn-default" href="${ctx }/notice/add">新增</a>
			</div>
		  </div>
		</div>
		<div class="panel-body">
			<table class="table table-hover table-striped">
				<thead>
					<tr>
						<th>标题</th>
						<th>作者</th>
						<th>撰写时间</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.content }" var="nr">
						<tr class="${nr.notice.status eq 'RELEASED' and empty nr.readTime? 'unread':'' }">
							<td>${nr.notice.title }</td>
							<td>${nr.notice.author.name }</td>
							<td><fmt:formatDate value="${nr.notice.writeTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>
								<c:choose>
									<c:when test="${nr.notice.status eq 'DRAFT' }">
										草稿
									</c:when>
									<c:when test="${nr.notice.status eq 'RECALL' }">
										撤回
									</c:when>
									<c:when test="${nr.notice.status eq 'RELEASED' }">
										已发布
									</c:when>
								</c:choose>
							</td>
							<td>
								<%-- 发布按钮：草稿状态可以发布 --%>
								<%-- 编辑：草稿状态可以编辑 --%>
								<%-- 删除：草稿状态可以删除 --%>
								<c:if test="${nr.notice.status eq 'DRAFT'}">
									<a class="btn btn-xs btn-primary" onclick="publishNotice('${nr.notice.id}')">发布</a>
									<a class="btn btn-xs btn-default" onclick="editNotice('${nr.notice.id}')">编辑</a>
									<a class="btn btn-xs btn-danger" onclick="deleteNotice('${nr.notice.id}')">删除</a>
								</c:if>
								<%-- 撤回：当前用户编写的、已经发布的可以撤回 --%>
								<%-- 阅读：发布状态可以阅读 --%>
								<c:if test="${nr.notice.status eq 'RELEASED' }">
									<c:if test="${nr.notice.author.id eq sessionScope['SPRING_SECURITY_CONTEXT'].authentication.principal.userId }">
										<a class="btn btn-xs btn-warning" onclick="recallNotice('${nr.notice.id}')">撤回</a>
									</c:if>
									<a class="btn btn-xs btn-info" onclick="readNotice('${nr.notice.id}')">阅读</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="5" style="text-align: center;">
							<fk:page url="/notice?keyword=${param.keyword }" page="${page }"/>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
	//发布
	function publishNotice(id){
		$.ajax({
			url: "${ctx}/notice/publish/"+id,
			method: "POST",
			success: function(){
				//成功就刷新页面
				window.location.reload();
			}
		})
	}
	
	//编辑
	function editNotice(id){
		window.location.href = "${ctx}/notice/edit/"+id;
	}
	
	//删除
	function deleteNotice(id){
		$.ajax({
			url: "${ctx}/notice/"+id,
			method: "DELETE",
			success: function(){
				window.location.reload();
			}
		})
	}
	
	//撤回
	function recallNotice(id){
		$.ajax({
			url: "${ctx}/notice/recall/"+id,
			method: "POST",
			success: function(){
				window.location.reload();
			}
		})
	}
	
	//阅读
	function readNotice(id){
		window.location.href = "${ctx}/notice/"+id;
	}
</script>
</body>
</html>