<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤管理</title>
	<meta name="decorator" content="default"/>
	<style>
		.img-box{
			width:100%;
    		height:100%;
    		background:rgba(0, 0, 0, 0.5);
		    position: fixed;
		    left: 0;
		    top: 0;
		    z-index: 99;
		    display:none;
		}
		.succ-pop{
		    width: 800px;
		    height: 600px;
		    background: #fff;
		    position: fixed;
		    left: 50%;
		    top: 50%;
		    margin-left: -400px;
		    margin-top: -300px;
		    z-index: 999;
		    border-radius: 5px;
		    text-align:center;
		}
		.succ-pop span{
			font-size: 34px;
		    color: #ffffff;
		    position: absolute;
		    top: 0;
		    right: -20px;
		    cursor: pointer;
		}
		.succ-pop img{
			width:720px;
			height:540px;
			margin-top: 30px;
		}   
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		$(function(){
			$(".watchImg").on('click',function(){
				$(".img-box").show();
				var imgSrc=$(this).attr('imgsrc');
				$(".succ-pop img").attr('src',imgSrc)
			})
			$(".succ-pop span").on('click',function(){
				$(".img-box").hide();
			})
		})
	</script>
</head>
<body>
	<c:choose>
		<c:when test="${not empty cclass or curUser.userType eq fns:getDictValue('kindergarten','sys_user_type','')}">
			<c:if test="${curUser.userType eq fns:getDictValue('teacher','sys_user_type','')}">
				<h6>今日出勤人数：${clockNum}/${allNum}</h6>
			</c:if>
			<form:form id="searchForm" modelAttribute="cWork" action="${ctx}/sys/cWork/" method="post" class="breadcrumb form-search">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<label>家长：</label>
					<sys:treeselect id="userId" name="userId" value="${cWork.userId}" labelName="cWork.userId" labelValue="${cWork.userName}"
							title="家长" url="/sys/user/treeDataForTeacher" allowClear="true"/>
				<c:if test="${curUser.userType eq fns:getDictValue('kindergarten','sys_user_type','')}">
					<label>班级：</label>
						<sys:treeselect id="classId" name="classId" value="${cWork.classId}" labelName="cWork.classId" labelValue="${cWork.className}"
								title="班级" url="/sys/cclass/treeData" allowClear="true" isAll="1"/>
				</c:if>
				<br/><br/>
				<label>日期范围：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${cWork.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<label>&nbsp;--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"
					value="<fmt:formatDate value="${cWork.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}',dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;&nbsp;
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>家长名称</th>
						<th>班级</th>
						<th>打卡时间</th>
						<th>体温</th>
						<th>拍照</th>
						<th>状态</th>
						<%-- <shiro:hasPermission name="sys:cWork:edit"><th>操作</th></shiro:hasPermission> --%>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="cWork">
					<tr>
						<td>${cWork.userName}</td>
						<td>${cWork.className}</td>
						<td><fmt:formatDate value="${cWork.signTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${cWork.signTemp}</td>
						<td>
							<c:if test="${not empty cWork.picUrl}">
								<%-- <img style="width: 200px;height: 150px;" src="${fns:getDictValue('work_upload_path','work_upload_path','')}${cWork.picUrl}"> --%>
								<a class="watchImg" href="javascript:void(0);" imgsrc="${cWork.picUrl}">查看图片</a>
							</c:if>
							
						</td>
						<td>
							<c:if test="${not empty cWork.signMode}">
								${cWork.signMode eq '65' ? '进园' : '出园'}
							</c:if>
						</td>
						<%-- <shiro:hasPermission name="sys:cWork:edit"><td>
		    				<a href="${ctx}/sys/cWork/form?id=${cWork.id}">修改</a>
							<a href="${ctx}/sys/cWork/delete?id=${cWork.id}" onclick="return confirmx('确认要删除该考勤吗？', this.href)">删除</a>
						</td></shiro:hasPermission> --%>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="pagination">${page}</div>
		</c:when>
		<c:otherwise>
			<h2>您还没有管理班级</h2>
			<h2>请联系园长开通管理权限</h2>
		</c:otherwise>
	</c:choose>
	<div class="img-box">
		<div class="succ-pop">
			<span>×</span>	
			<img src="">
		</div>
	</div>
</body>
</html>