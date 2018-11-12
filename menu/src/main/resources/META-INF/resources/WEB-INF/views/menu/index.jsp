<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>菜单管理</title>
<link rel="stylesheet" href="${ctx }/webjars/bootstrap/3.3.7/dist/css/bootstrap.min.css"/>
<script type="text/javascript" src="${ctx }/webjars/jquery/3.3.1/dist/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/webjars/bootstrap/3.3.7/dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${ctx }/static/css/fkjava.css">
<link rel="stylesheet" href="${ctx }/zTree/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${ctx }/zTree/js/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/menu.js"></script>
<link rel="stylesheet" href="${ctx }/static/css/menu.css"/>
</head>
<body>
<div class="container-fluid">
	<div class="panel panel-primary">
		<div class="panel-heading">
		  	<div class="col-md-12 headText">
		  		<div class="col-md-10 col-xs-6">
		  			菜单管理
			  	</div>
		  	</div>
		</div>
		<div class="panel-body">
			<div class="col-xs-12 col-sm-4 tree-container">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
			<div class="col-xs-12 col-sm-8 menu-form">
				<form class="col-xs-12 col-md-offset-2" style="width: 70%" 
						action="" method="post" id="menu-form">
				  <input type="hidden" name="id"/>
				  <div class="form-group col-sm-12">
				    <label for="name" class="col-sm-4 control-label">上级菜单</label>
				    <div class="col-sm-8">
				    	<span id="parentName"></span>
				    	<input type="hidden" name="parent.id" id="parentId">
				    </div>
				  </div>
				  <div class="form-group col-sm-12">
				    <label for="Name" class="col-sm-2 control-label">菜单名称</label>
				    <input type="text" class="form-control col-sm-10" 
				    		name="name" placeholder="菜单" style="width: 60%"/>
				  </div>
				  <div class="form-group col-sm-12">
				    <label for="Name" class="col-sm-2 control-label">URL</label>
				    <input type="text" class="form-control col-sm-10" 
				    		name="url" placeholder="访问菜单时使用的URL" style="width: 60%"/>
				  </div>
				  <div class="col-sm-12">
						<div class="form-group">
						    <label for="inputURL" class="col-sm-2 control-label">类型</label>
						    <div class="col-sm-10">
						        <div class="radio">
									<label>
										<input type="radio" name="type" value="LINK"/>
										链接类型的菜单，用于显示出来给用户点击使用的
									</label>
								</div>
						        <div class="radio">
									<label>
										<input type="radio" name="type" value="BUTTON"/>
										用于在主页面中，作为用户是否有权限操作此按钮
									</label>
								</div>
						    </div>
						</div>
					</div>
				  <div class="from-group col-sm-12">
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
				  <div class="col-sm-12 text-right">
						<button class="btn btn-default reset-button" type="button">重置</button>
						<button class="btn btn-primary">保存</button>
				  </div>
				</form>
			</div>
		</div> 
	</div>
</div>

</body>
</html>