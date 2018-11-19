<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>发布公告</title>
<link rel="stylesheet" href="${ctx }/static/css/fkjava.css">
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
			
			<form action="${ctx }/notice" method="post" onsubmit="return checkContent();">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input type="hidden" name="id" value="${notice.id }">
				<div class="form-group ">
				    <label for="notice-title">公告标题</label>
				    <input type="text" class="form-control" 
				    		name="title" placeholder="请输入公告标题"
				    		required="required" style="width: 80%" value="${notice.title }">
				</div>
				
				<select class="btn btn-default" id="select" name="type.id" required="required">
					<option>请选择公告类型</option>
					<c:forEach items="${types }" var="t">
					<option value="${t.id }" ${notice.type.id eq t.id? 'selected="selected"':'' }>${t.name }</option>
					</c:forEach>
				</select>
				<div class="" id="noticeContentEditor">${notice.content }</div>
				<textarea name="content" id="noticeContent" style="display: none">${notice.content }</textarea>
				<button class="btn btn-default">保存</button>
			</form>
			
			
		</div>
	</div>
</div>
<script type="text/javascript" src="${ctx }/webjars/wangEditor/3.1.1/release/wangEditor.min.js"></script>
<script type="text/javascript">
    $(function(){
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
        
        //debug
        // editor.customConfig.debug = true;
        
        // 接收改变后的内容，获取富文本编辑器里面的内容，放到#noticeContent里面
        editor.customConfig.onchange = function(html){
        	$("#noticeContent").val(html);
        };
        // 粘贴图片
        editor.customConfig.pasteIgnoreImg = false;
        // 不要过滤复制内容的样式，保持原本的样式，可能有些时候不能完全得到样式，此时可以自定义外观（写CSS）
        editor.customConfig.pasteFilterStyle = false;
        
        // 创建编辑器
        editor.create();
       
    })
  
  function checkContent(){
    	var text = $("#noticeContentEditor").text().trim();
    	if(text === ""){
    		alert("请填写公告内容");
    		return false;
    	}
    	return true;
    }
</script>
</body>
</html>