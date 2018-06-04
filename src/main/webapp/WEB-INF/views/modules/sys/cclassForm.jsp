<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班级表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			
			$("#year").val(new Date().getYear() + 1900);
			
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
		<li><a href="${ctx}/sys/cclass/">班级列表</a></li>
		<li class="active"><a href="${ctx}/sys/cclass/form?id=${cclass.id}">班级<shiro:hasPermission name="sys:class:edit">${not empty cclass.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:class:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="CClass" action="${ctx}/sys/cclass/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">监控名称：</label>
			<div class="controls">
				<form:input path="monitorName" htmlEscape="false" maxlength="64" class="input-xlarge"/>
				<span class="help-inline"> 第三方指定名称</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入学年份：</label>
			<div class="controls">
				<form:input path="year" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> 如：2018</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责教师：</label>
			<div class="controls">
				 <sys:treeselect id="teacherId" name="teacherId" value="${CClass.teacherId}" labelName="CClass.teacherId" labelValue="${CClass.teacherName}"
					title="教师" url="/sys/user/treeDataForClass" allowClear="true" notAllowSelectParent="true" cssClass="required"/>
				<span class="help-inline"><font color="red">*</font> 无教师可选需添加教师（不可继续操作）</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学段：</label>
			<div class="controls">
				<form:select path="period" class="input-medium">
					<form:options items="${fns:getDictList('sys_period_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:class:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>