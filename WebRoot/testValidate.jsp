<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>测试客户端数据校验</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-1.8.3.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/script/validate/validate.js"></script>
 	<script type="text/javascript">
 		$().ready(function(){
 			$("#form1").validate({
 			rules:{
 				username:{
 					required:true,
 				},
 				
 				password:{
 					required:true,
 					minlength:3,
 				},
 				
 				repassword:{
 					required:true,
 					minlength:3,
 					equalTo:"#password",
 				},
 				
 				email:{
 					required:true,
 					email:true,
 				},
 				}
 				}
 			
 			);
 		});
 	</script>
 
  </head>
  
  <body>
    	<form action="xxx" name="form1" id="form1">
    		<table>
    			<tr>
    				<td>
    					用户名
    				</td>
    				
    				<td>
    					<input type="text" name="username" id="username">
    				</td>
    			</tr>
    			<tr>
    				<td>
    					密码
    				</td>
    				
    				<td>
    					<input type="password" name="password" id="password">
    				</td>
    			</tr>
    			
    			<tr>
    				<td>
    					重复密码
    				</td>
    				
    				<td>
    					<input type="password" name="repassword" id="repassword">
    				</td>
    			</tr>
    			<tr>
    				<td>
    					邮箱
    				</td>
    				
    				<td>
    					<input type="text" name="email" id="email">
    				</td>
    			</tr>
    		</table>
    		<input type="submit" value="提交">
    	</form>
  </body>
</html>
