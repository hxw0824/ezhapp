<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>保存商品成功管理</title>
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
		<li class="active"><a href="${ctx}/products/products/">商品列表</a></li>
		<shiro:hasPermission name="products:products:edit"><li><a href="${ctx}/products/products/form">商品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="products" action="${ctx}/products/products/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>商品编码：</label>
				<form:input path="psncode" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>

			<li>
				<label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="">--全部--</form:option>
					<form:option value="0">下架</form:option>
					<form:option value="1">上架</form:option>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品编码</th>
				<th>商品名称</th>
				<th>原价</th>
				<th>销售价</th>
				<th>状态</th>
				<shiro:hasPermission name="products:products:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="products">
			<tr>
			
				<td>
					<a href="${ctx}/products/products/form?id=${products.id}">
						${products.psncode }
					</a>
				</td>
				<td>
					${products.pname}
				</td>
				<td>
					${products.originalPrice}
				</td>
				<td>
					${products.salePrice}
				</td>

				
				<td>
					<c:if test="${products.status == '0'}">下架</c:if>
					<c:if test="${products.status == '1'}">上架</c:if>
				</td>
				<shiro:hasPermission name="products:products:edit"><td>
    				<a href="${ctx}/products/products/form?id=${products.id}">修改</a>
					<a href="${ctx}/products/products/delete?id=${products.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>