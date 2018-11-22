<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>假期类型</title>
</head>
<body>
<div class="container-fluid">
	<div class="panel panel-primary ">
		  <div class="panel-heading">
		  	<div class="col-md-12 headText">
		  		<div class="col-md-10 col-xs-6">
		  			请假类型管理
			  	</div>
		  	</div>
		  </div>
		  <div class="panel-body" id="body">
		  	<div class="col-sm-12 col-md-4">
		  	</div>
		  	<div class="col-sm-12 col-md-8 type-form">
		  		<!-- 类型修改的表单 -->
		  		<form class="col-xs-12 col-md-offset-3" style="width: 50%" 
						action="" method="post" id="typeForm">
				  <!-- <input type="hidden" name="id"> -->
				  <div class="form-group">
				    <label for="exampleInputEmail1">请假类型</label>
				    <input type="text" class="form-control" 
				    		name="name" placeholder="请输入请假类型"
				    		required="required" >
				  </div>
				  <div class="form-group">
				    <label for="exampleInputEmail1">工资扣除比例</label>
				    <input type="text" class="form-control" 
				    		name="scale" placeholder="请输入扣除比例  %"
				    		required="required" >
				  </div>
				  <input type="hidden"
						name="${_csrf.parameterName}"
						value="${_csrf.token}"/>
				  <button id="submit" class="btn btn-default">新增</button>
				  <button id="alter" style="display: none;" class="btn btn-default">修改</button>
				</form>
				  
		  	</div>
		    
		  </div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$("input[name='scale']").change(function(){
			var value = $(this).val();
			var check = /^[\d]{1,3}$/;
			if(value > 100 || value < 0){
				alert("请输入0-100之间的数");
			}
			if(!check.test(value) ){
				alert("请输入数字");
			}
			
			
		})
		
		$("#typeForm").submit(function(){
			var value = $("input[name='scale']").val();
			value = value * 0.01;
			$("input[name='scale']").val(value);
		})
	})
</script>
</body>
</html>