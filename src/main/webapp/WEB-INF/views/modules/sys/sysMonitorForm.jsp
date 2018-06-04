<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>监控表管理</title>
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
		<li><a href="${ctx}/sys/sysMonitor/">监控列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysMonitor/form?id=${sysMonitor.id}">监控<shiro:hasPermission name="sys:sysMonitor:edit">${not empty sysMonitor.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysMonitor:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysMonitor" action="${ctx}/sys/sysMonitor/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="officeId"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">班级：</label>
			<div class="controls">
				 <sys:treeselect id="classId" name="classId" value="${sysMonitor.classId}" labelName="sysMonitor.className" labelValue="${sysMonitor.className}"
					title="班级" url="/sys/cclass/treeData" allowClear="true" isAll="1"/>
				<span class="help-inline"><font color="red">不选择班级默认为公共摄像头</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备编号：</label>
			<div class="controls">
				<form:input path="deviceId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备IP：</label>
			<div class="controls">
				<form:input path="ip" htmlEscape="false" maxlength="2000" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开启时间：</label>
			<div class="controls">
				<input id="beginTime" name="beginTime" type="text" maxlength="20" class="input-mini Wdate required"
					value="${sysMonitor.beginTime}" onclick="WdatePicker({dateFmt:'H',isShowClear:false,maxDate:'#F{$dp.$D(\'endTime\')}'});"/>
				<label>&nbsp;&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endTime" name="endTime" type="text" maxlength="20" class="input-mini Wdate required"
					value="${sysMonitor.endTime}" onclick="WdatePicker({dateFmt:'H',isShowClear:false,minDate:'#F{$dp.$D(\'beginTime\')}'});"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status">
					<form:options items="${fns:getDictList('sys_status_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> 设备启用状态</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysMonitor:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>