<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>测试用datatables来进行页面的分页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/script/dataTables/css/jquery.dataTables.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/dataTables/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/dataTables/js/jquery.dataTables.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#table1")
								.dataTable(
										{
											bFilter : true,
											bSort : true,
											bLengthChange : true,
											oLanguage : {
												"sLengthMenu" : "每页显示 _MENU_条",
												"sZeroRecords" : "没有找到符合条件的数据",
												"sProcessing" : "&lt;img src=’./loading.gif’ /&gt;",
												"sInfo" : "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
												"sInfoEmpty" : "有木记录",
												"sInfoFiltered" : "(从 _MAX_ 条记录中过滤)",
												"sSearch" : "搜索：",
												"oPaginate" : {
													"sFirst" : "首页",
													"sPrevious" : "前一页",
													"sNext" : "后一页",
													"sLast" : "尾页"
												}
											},
											//从服务器动态获取数据
											bServerSide : true,//开启服务器支持,注意，是bServerSide，不是bServiceSide
											sAjaxSource : "${pageContext.request.contextPath}/system/elecUserAction_test.action?tempStr="
													+ new Date().getTime(),//指定请求路径
											//向服务器发送请求时调用,用来添加额外的请求参数
											//指定请求参数(使用回调函数,在请求提交之前加入额外的请求参数)
											fnServerParams : function(aoData) {
												aoData.push({
													"name" : "username",
													"value" : "hhm"
												});
											},

											//指定如何使用ajax请求服务器(也就是说dataTable本身并不知道如何请求)
											//需要从服务器请求数据时调用(如何使用ajax进行请求)
											fnServerData : function(sSource,
													aoData, fnCallback,
													oSettings) {
												oSettings.jqXHR = $.ajax({
													"dataType" : 'json',
													"type" : "POST",
													"url" : sSource,
													"data" : aoData,
													"success" : fnCallback
												});
											},
											"columns" : [ {
												"data" : "account"
											}, {
												"data" : "username"
											}, {
												"data" : "isDuty"
											} ]
										});
					});
</script>
</head>

<body>
	<table id="table1" border="1">
		<!-- 要用thead来做表头才会有分页的效果 -->
		<thead>
			<td>账号</td>
			<td>用户名</td>
			<td>是否在职</td>
			
		</thead>

		<!-- 
		<tr>
			<td>Name1</td>
			<td>hhm</td>
			<td>男</td>
			<td>188</td>
			<td>在职</td>
			<td>管理员</td>
		</tr>
	 -->






	</table>
</body>
</html>
