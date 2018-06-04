<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/order/order/">订单列表</a></li>
		<shiro:hasPermission name="order:order:edit"><li><a href="${ctx}/order/order/form">订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/order/order/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>订单编号：</label>
				<form:input path="orderNumber" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>订单号</th>
				<th>支付宝交易号</th>
				<th>商品编码</th>
				<th>购买人</th>
				<th>原价</th>
				<th>购买价</th>
				<th>支付时间</th>
				<th>到期时间</th>
				<th>订单状态</th>
				<th>交易状态</th>
				<shiro:hasPermission name="order:order:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="order">
			<tr>
			<td>
					<a href="${ctx}/order/order/form?id=${order.id}">
						${order.orderNumber }
					</a>
				</td>
				<td>
					${order.tradeno}
				</td>
				<td>
					${order.psncode}
				</td>
				<td>
					${order.cuser.loginName}
				</td>
				<td>
					${order.product.originalPrice}
				</td>
				<td>
					${order.salePrice}
				</td>
				<td>
					${order.payTime}
				</td>
				<td>
					${order.expireTime}
				</td>
				<td>
				<c:if test="${order.isPay == '0'}">待支付</c:if>
				<c:if test="${order.isPay == '1'}">已支付</c:if>
					
				</td>
				<td>
				<c:if test="${order.status == '0'}">已完成</c:if>
				<c:if test="${order.status == '1'}">交易中</c:if>
				</td>
				<shiro:hasPermission name="order:order:edit"><td>
    				<a href="${ctx}/order/order/form?id=${order.id}">修改</a>
					<a href="${ctx}/order/order/delete?id=${order.id}" onclick="return confirmx('确认要删除该订单吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>