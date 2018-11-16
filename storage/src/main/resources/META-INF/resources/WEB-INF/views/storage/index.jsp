<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--引用tag文件 --%>
<%@taglib prefix="fk" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文件管理</title>
<link rel="stylesheet" href="${ctx }/static/css/fkjava.css">
</head>
<body>
<div class="container-fluid">
	<div class="panel panel-primary">
		  <div class="panel-heading">
		  	<div class="col-md-12 headText">
		  		<div class="col-md-10 col-xs-6">
		  			文件列表
			  	</div>
			  	<div class="col-md-2 col-xs-6">
			  		<a class="btn btn-default" data-toggle="modal" data-target=".file-upload-dialog" >新增</a>
			  	</div>
		  	</div>
		  </div>
		  <div class="panel-body">
		    <table class="table table-striped table-hover">
			  <thead>
			  	<tr>
			  		<th>文件名</th>
			  		<th>文件类型</th>
			  		<th>文件大小</th>
			  		<th>上传时间</th>
			  		<th>操作</th>
			  	</tr>
			  </thead>
			  <tbody>
			  	<c:forEach items="${page.content }" var="f">
			  		<tr>
			  			<td>${f.name }</td>
			  			<td>${f.contentType }</td>
			  			<td>${f.fileSize }</td>
			  			<td>${f.uploadTime }
			  			<td>下载/删除</td>
			  		</tr>
			  	</c:forEach>
			  </tbody>
			</table>
			<%--前缀随意写要与tag指令的前缀相同，后缀要与文件名相同 --%>
			<fk:page page="${page }"/>
		  </div>
	</div>
</div>
<div class="modal fade file-upload-dialog" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
        	<form action="" method="post" enctype="multipart/form-data">
        		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                	<span aria-hidden="true">&times;</span>
	               	</button>
	                <h4 class="modal-title">上传文件</h4>
	            </div>
	            <div class="modal-body">
	                <p>请选择要上传的文件，文件大小不能超过10M。</p>
	                <div class="form-group">
					    <label for="uploadFile">选择文件</label>
					    <%-- 这里需要使用JS读取文件大小，限制文件的大小不能超过10M。此乃坑也！ --%>
					    <input type="file" id="uploadFile" name="file" required="required"/>
					    <p class="help-block">自己上传的文件，只有自己能够看到。</p>
					</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                <button type="submit" class="btn btn-primary">上传</button>
	            </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>