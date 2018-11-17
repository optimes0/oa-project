<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>发布公告</title>
</head>
<body>
<div class="container-fluid">
	<div class="panel panel-primary">
		<!-- Default panel contents -->
		<div class="panel-heading">
		  <div class="col-md-12 headText">
			<div class="col-md-10 col-xs-6">
		  			编辑公告
			</div>
		  </div>
		</div>
		<div class="panel-body">
			<div id="noticeContentEditor"></div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx }/webjars/wangEditor/3.1.1/release/wangEditor.min.js"></script>
<script type="text/javascript">
    var E = window.wangEditor;
    var editor = new E('#noticeContentEditor');
    // 或者 var editor = new E( document.getElementById('editor') );
    
    // 创建编辑器
    editor.create();
</script>
</body>
</html>