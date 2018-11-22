<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部门管理</title>
<link rel="stylesheet" href="${ctx }/zTree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${ctx }/zTree/js/jquery.ztree.all.min.js"></script>
<link rel="stylesheet" href="${ctx }/static/css/menu.css"/>
</head>
<body>
<div class="container-fluid">
	<div class="panel panel-primary">
		<div class="panel-heading">
		  	<div class="col-md-12 headText">
		  		<div class="col-md-10 col-xs-6">
		  			部门管理
			  	</div>
		  	</div>
		</div>
		<div class="panel-body">
			<div class="col-xs-12 col-sm-4 tree-container">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
			<div class="col-xs-12 col-sm-8 hr-form">
				<form class="col-xs-12 col-md-offset-2" style="width: 70%" 
						action="" method="post" id="hr-form">
				  <input type="hidden" name="id"/>
				  <div class="form-group col-sm-12">
				    <label class="col-sm-4 control-label">上级部门</label>
				    <div class="col-sm-8">
				    	<span id="parentName"></span>
				    	<input type="hidden" name="parent.id" id="parentId">
				    </div>
				  </div>
				  <div class="form-group col-sm-12">
				    <label class="col-sm-3 control-label">部门名称</label>
				    <input type="text" class="form-control col-sm-9" 
				    		name="name" placeholder="菜单" style="width: 60%"/>
				  </div>
				  <div class="form-group col-sm-12">
				    <label class="col-sm-3 control-label">部门经理</label>
				    <input type="text" 
					         class="form-control" 
					         id="selectManager"/>
			        <input type="hidden" 
			        	id="managerUserId"
			        	name="manager.user.id"/>
				  </div>
				  <div class="col-sm-12 text-right">
						<button class="btn btn-default reset-button" type="button">重置</button>
						<button class="btn btn-primary">保存</button>
				  </div>
				  <input type="hidden"
					name="${_csrf.parameterName}"
					value="${_csrf.token}"/>
				</form>
			</div>
		</div> 
	</div>
</div>
<script type="text/javascript" src="${ctx }/webjars/devbridge-autocomplete/1.4.8/dist/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="${ctx }/js/department.js"></script>
</body>
</html>