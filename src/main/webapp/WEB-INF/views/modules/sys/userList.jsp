<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
			
			$("input[id^='bindCard_']").focus(function(){
				global_old_card = $(this).val().length == 10 ? $(this).val() : "";
			});
			
			$("input[id^='bindCard_']").blur(function(){
				var uid = $(this).attr("uid");
				var index = $(this).attr("index");
				$("#card_img_"+index).attr("src","");
				$("#card_msg_"+index).html("").hide();
				if(global_success_status && $("#card_unbind_"+index).html() == undefined){
					$("#card_img_"+index).after("<a id='card_unbind_"+index+"' index='"+index+"' uid='"+uid+"' href='javascript:void(0);'>解绑</a>");
					bindFct();
				}
				if(!global_success_status)$(this).val(global_old_card);
				global_success_status = false;
			});
			
			bindFct();
		});
		
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/list");
			$("#searchForm").submit();
	    	return false;
	    }
		
		function bindFct(){
			$("a[id^='card_unbind_']").click(function(){
				var uid = $(this).attr("uid");
				var index = $(this).attr("index");
				var obj = $(this);
				$.ajax({
		  			url : '${ctx}/sys/user/unbindCard',
		  			type : 'GET',
		  			dataType : 'json',
		  			data : {
		  				userId : uid		  				
		  			},
		  			success : function(data){
		  				if(data.status == 1){
		  					$("#bindCard_"+index).val("");
		  					obj.remove();
		  				}
		  			},
		  			error : function(e){
		  				alert(e);
		  			}
		  		});
			});
		}
		
		function initPwd(id){
			$.post("initPwd", { 'id' : id }, function(data){
				alertx("‘"+data+"’ 密码已重置为：123456");
			} );
		}
		
		var reg = /^[0-9]*[1-9][0-9]*$/;
		var global_old_card = '';
		var global_success_status = false;
		function bindCard(obj){
			var cardId = $(obj).val();
			var uid = $(obj).attr("uid");
			var index = $(obj).attr("index");
			
			$("#card_img_"+index).attr("src","");
			$("#card_msg_"+index).html("").hide();
 			if(global_success_status && $("#card_unbind_"+index).html() == undefined){
				$("#card_img_"+index).after("<a id='card_unbind_"+index+"' index='"+index+"' uid='"+uid+"' href='javascript:void(0);'>解绑</a>");
				bindFct();
			}
			
			if(cardId.length != 10 || global_old_card == cardId)return;
			
			//global_success_status = false;
		  	if(reg.test(cardId)){
		  		$.ajax({
		  			url : '${ctx}/sys/user/bindCard',
		  			type : 'GET',
		  			dataType : 'json',
		  			data : {
		  				id : cardId,
		  				userId : uid		  				
		  			},
		  			success : function(data){
		  				if(data.status == 1){
		  					global_old_card = cardId;
		  					global_success_status = true;
		  					timerShow(index,data.status);
		  				}else if(data.status == 2){
		  					console.log(data.user);
		  					timerShow(index,data.status,"IC卡已绑定："+data.user.loginName+"（"+ data.user.name +"）");
		  				}else{
		  					timerShow(index,data.status);
		  				}
		  			},
		  			error : function(e){
		  				alert(e);
		  			}
		  		});
		  	}else{
		  		timerShow(index,2,"IC卡格式错误【10位数字】");
		  	}
		}
		
		//var timer;
		function timerShow(index,status,message){
			var uid = $("#bindCard_"+index).attr("uid");
			var iconName = "";
			if(status == 0){
				iconName = "error";
			}else if(status == 1){
				iconName = "success";
			}
			
			if(iconName != '')$("#card_img_"+index).attr("src","${ctxStatic}/images/" + iconName + "_icon.png");
			if(message != undefined)$("#card_msg_"+index).html(message).show();
			/* timer = setTimeout(function(){
				$("#card_img_"+index).attr("src","");
				$("#card_msg_"+index).html("").hide();
				if(status == 1 && $("#card_unbind_"+index).html() == undefined)$("#card_img_"+index).after("<a id='card_unbind_"+index+"' index='"+index+"' uid='"+uid+"' href='javascript:void(0);'>解绑</a>");
			},5000);
			
			bindFct(); */
		}
	</script>
	<style>
		.mini-input {
		    width: 80px;
		    margin-top: 1px;
		}
	</style>
</head>
<body>
<c:choose>
	<c:when test="${not empty cclass or currentUser.userType eq fns:getDictValue('kindergarten','sys_user_type','') or currentUser.userType eq fns:getDictValue('sysadmin','sys_user_type','') or currentUser.userType eq fns:getDictValue('admin','sys_user_type','')}">
		<div id="importBox" class="hide">
			<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
				class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
				<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
				<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
				<a href="${ctx}/sys/user/import/template">下载模板</a>
			</form>
		</div>
		<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/sys/user/list">用户列表</a></li>
			<shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/user/form">用户添加</a></li></shiro:hasPermission>
		</ul>
		<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="breadcrumb form-search ">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
			<ul class="ul-form">
				<li><label>归属公司：</label><sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
					title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/></li>
				<li><label>登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
				<li><label>手机：</label><form:input path="mobile" htmlEscape="false" maxlength="20" class="input-medium"/></li>
				<li class="clearfix"></li>
				<li><label>归属部门：</label><sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/></li>
				<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
				<li><label>卡号：</label><form:input path="cardId" htmlEscape="false" maxlength="10" class="input-medium"/></li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
					<shiro:hasPermission name="sys:user:import">
						<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
						<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
					</shiro:hasPermission>
				</li>
				<li class="clearfix"></li>
			</ul>
		</form:form>
		<sys:message content="${message}"/>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead><tr>
				<th width="10%">归属公司</th>
				<th width="10%">归属部门</th>
				<th width="10%">归属班级</th>
				<th class="sort-column user_type" width="7%">用户角色</th>
				<th class="sort-column login_name" width="8%">登录名</th>
				<th class="sort-column name" width="8%">姓名</th>
				<th width="8%">手机</th>
				<shiro:hasPermission name="sys:user:edit"><th width="10%">操作</th></shiro:hasPermission>
				<shiro:hasPermission name="sys:user:import"><th width="30%">绑卡（10位数字）</th></shiro:hasPermission></tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="user" varStatus="uindex">
				<tr>
					<td>${user.company.name}</td>
					<td>${user.office.name}</td>
					<td>${user.className}</td>
					<td>${fns:getDictLabel(user.userType,'sys_user_type','')}</td>
					<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
					<td>${user.name}</td>
					<td>${user.mobile}</td>
					<shiro:hasPermission name="sys:user:edit"><td>
	    				<a href="${ctx}/sys/user/form?id=${user.id}">修改</a>
						<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
						<a href="javascript:initPwd('${user.id}')">重置密码</a>
					</td></shiro:hasPermission>
					<shiro:hasPermission name="sys:user:import"><td>
						<c:if test="${user.userType eq fns:getDictValue('parent','sys_user_type','')}">
							<label for="bindCard_${uindex.count}">IC卡号：</label><input id="bindCard_${uindex.count}" index="${uindex.count}" uid="${user.id}" type="text" class="mini-input" value="${user.cardId}" maxlength="10" oninput="bindCard(this)"/>
							<c:if test="${not empty user.cardId}"><a id="card_unbind_${uindex.count}" uid="${user.id}" index="${uindex.count}" href="javascript:void(0);">解绑</a></c:if>
							<img id="card_img_${uindex.count}" src="" /><label class="error" style="display:none;" id="card_msg_${uindex.count}"></label>
						</c:if>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="pagination">${page}</div>
	</c:when>
	<c:otherwise>
		<h2>您还没有管理班级</h2>
		<h2>请联系园长开通管理权限后添加家长</h2>
	</c:otherwise>
</c:choose>
</body>
</html>