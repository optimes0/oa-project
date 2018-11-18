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
			<input type="hidden" name="content" id="noticeContent"/>
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx }/webjars/wangEditor/3.1.1/release/wangEditor.min.js"></script>
<script type="text/javascript">
    var E = window.wangEditor;
    var editor = new E('#noticeContentEditor');
    // 或者 var editor = new E( document.getElementById('editor') );
    
    // 隐藏网络图片引用tab
    //editor.customConfig.showLinkImg = false;
    
    // 显示图片上传的tab
    editor.customConfig.uploadImgServer = '${ctx}/storage/file/wangEditor';
    // 上传的时候，文件的字段名
    editor.customConfig.uploadFileName = 'file';
    // 自定义上传的时候请求头内容
    editor.customConfig.uploadImgHeaders = {
   	    '${_csrf.headerName}': '${_csrf.token}'
   	};
    
    // 接收改变后的内容，获取富文本编辑器里面的内容，放到#noticeContent里面
    editor.customConfig.onchange = function(html){
    	$("#noticeContent").val(html);
    };
    // 粘贴图片
    editor.customConfig.pasteIgnoreImg = true;
    // 不要过滤复制内容的样式，保持原本的样式，可能有些时候不能完全得到样式，此时可以自定义外观（写CSS）
    editor.customConfig.pasteFilterStyle = false;
    
    // 创建编辑器
    editor.create();
</script>
</body>
</html>