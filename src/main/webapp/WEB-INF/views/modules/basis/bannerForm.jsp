<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>焦点图管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			$("#file").change(function(){
				if($("#file").val() != ""){
					var input_file = document.getElementById("file");
					var input_actionType = document.createElement("input");
					input_actionType.type="hidden";
					input_actionType.name="actionType";
					input_actionType.value="banner";
					var form = document.createElement("form");
					form.action="${ctx}/sys/upload";
					form.method="POST";
					form.enctype="multipart/form-data";
					form.target="fileFrame";
					form.appendChild(input_file);
					form.appendChild(input_actionType);
					form.submit();
				}
			});
		});
		function onUploadSuccess(resourceURL){
			$("#image").attr("src",resourceURL);
			$("#imageUrl").val(resourceURL);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/basis/banner/">焦点图列表</a></li>
		<li class="active"><a href="${ctx}/basis/banner/form?id=${banner.id}">焦点图<shiro:hasPermission name="basis:banner:edit">${not empty banner.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="basis:banner:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="banner" action="${ctx}/basis/banner/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<input type="hidden" id="imageUrl" name="imageUrl" />
				<img id="image" style="max-width:300px;" alt="" src="<c:if test="${not empty banner.id }">${banner.imageUrl }</c:if>">
				<input id="file" name="file" type="file" accept="image/*" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接地址：</label>
			<div class="controls">
				<form:input path="link" htmlEscape="false" maxlength="500" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="5" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="basis:banner:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<iframe id='fileFrame' name='fileFrame' style="display: none;"/>
</body>
</html>