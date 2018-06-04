<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生成授权卡</title>
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/basis/card/">授权卡列表</a></li>
		<li class="active"><a href="${ctx}/basis/card/form?id=${card.id}">生成授权卡<shiro:hasPermission name="basis:card:edit">${not empty card.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="basis:card:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="card" action="${ctx}/basis/card/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">卡号:</label>
			<div class="controls">
				<input disabled="disabled"style="background: #E1E1EB; font-style: italic;" value="系统自动生成"
						size="25" />
				<br/>
				&nbsp;&nbsp;&nbsp;<label style="color: #F00;">1.卡号为11位字符，不可重复，全库唯一代码（由1位学段编号+5位批次号编码+5位顺序编码组成）</label><br />
				&nbsp;&nbsp;&nbsp;<label style="color: #F00;">2.密码为16位字符，不可重复，全库唯一代码（卡号11位+5位随机码组成）</label><br />
						
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">对应学段：</label>
			<div class="controls">
				<form:select path="periodId">
					<form:option value="0">小班</form:option>
					<form:option value="1">中班</form:option>
					<form:option value="2">大班</form:option>
				</form:select>			
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生成数量：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批次名称：</label>
			<div class="controls">
				<form:input path="batchName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" rows="6"
							style="width: 60%" maxlength="100"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="basis:card:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>