<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
		<title>角色权限管理</title>		
		<LINK href="${pageContext.request.contextPath }/css/Style.css"  type="text/css" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath }/script/jquery-1.8.3.js"></script>
		<script type="text/javascript">
		
		    //简洁的角色列表,不包含权限信息
       var simpleRoleArray = [{"roleId":"001","name":"普通员工"},{"roleId":"002","name":"项目组长"},{"roleId":"003","name":"经理"},{"roleId":"004","name":"系统管理员"},{"roleId":"005","name":"超级管理员"}];
       
		    
		 function updateRole(){
			 var roleId = $("#selectRole").val();
			 if(!roleId){
				 return;
			 }
			 
			 var functionIds = "";
		 	$("[name=functions]").each(function(index,dom){
		 		if($(dom).attr('checked')){
		 			functionIds +=$(dom).val()+",";
				}
		 	});
		 	
		 	 $.post("${pageContext.request.contextPath}/system/elecAuthAction_updateRole.action",{'roleId':roleId,'functionIds':functionIds,'timestamp':new Date().getTime()},function(data){
					alert(data);
    		  });
		}
		
       
       function selectRole(){
    	   
    	   var roleId = $("#selectRole").val();
    	  
    	   if(!roleId){
    	   		
    		   $("[name=functions]").each(function(index,domElememt){
    		   		$(domElement).attr('check','null');
    		   });
    	   }else{
    		   
    		   $.post("${pageContext.request.contextPath}/system/elecAuthAction_selectRole.action",{'roleId':roleId,'timestamp':new Date().getTime()},function(data){
    			  
					$("[name=functions]").each(function(index,dom){
					
						var value = dom.getAttribute("value");
						var index2=data.indexOf(value);
						if(index2>-1){
							 $(dom).attr('checked',"checked");
						}else{
							 $(dom).attr('checked',null);
						}
					});		   
    		   });
    	   }
       }
     
      //var roleArray = [{'roleId':'001','rolename':'超级管理员'}  ,  ...];
	//var roleArray = <s:property value="%{#request.roleListJson}"  escapeHtml="false"/>;
	var roleArray =<s:property value="%{#request.roleListJson}"  escapeHtml="false"/>;
	//根据用户名模糊查询(ajax)
	function queryUser(){
		var username = $("#username").val();
		
		
		if(!username){
			return;
		}
		var url = "${pageContext.request.contextPath }/system/elecAuthAction_queryUser.action";
		$.post(url , {"username" : username ,"timestamp" : new Date().getTime()} , function(data){
				initTable(data);
		});
		
	}
	
	function initTable(userArray){
		//先清空旧数据
		$("#userRoleTable").empty();
		for(var i in userArray){
			var user = userArray[i];
			var html = "";
			html += '<tr><td>'+user.username+':</td><td>';
			html += '<form id="form'+i+'">';
			html += '	<input type="hidden" name="userId" value="'+user.userId+'" />';
			for(var j in roleArray){
				var role = roleArray[j];
				//标记,表示当前用户是否拥有当前角色
				var mark = false;
				//当前用户所拥有的角色
				var userRoleArray = user.roles;
				if(userRoleArray){
					for(var m in userRoleArray){
						var userRole = userRoleArray[m];
						if(userRole.roleId == role.roleId){
							mark = true;
							break;
						}
					}
				}
				if(mark){
					html += ' <input type="checkbox" name="roles" value="'+role.roleId+'" checked="checked" />'+role.rolename;
				}else{
					html += ' <input type="checkbox" name="roles" value="'+role.roleId+'"  />'+role.rolename;	
				}
			}
			html += ' </form>';
			html += '	</td><td><button onclick="updateUserRole('+i+')">保存修改</button></td></tr>';
			
			$("#userRoleTable").append(html);
		}
	}
	
       
       function updateUserRole(i){
    	   var queryString=$("#form"+i).serialize();//拼接表单中的所有查询参数
    	 var url="${pageContext.request.contextPath }/system/elecAuthAction_updateUserRole.action?"+queryString;
    	 $.post(url,{"timestamp":new Date().getTime()},function(data){
    	 		alert(data);
    	 });
       }
       
     
	</script>
</head>
	
	<body>
	
 	<div align="center" >
 <div  align="center"  style="width: 90%;background-color: #f5fafe;height: 100%" >

   <fieldset style="width:100%; border : 1px solid #73C8F9;text-align:left;color:#023726;font-size: 12px;min-height: 40%">
   	<legend align="left">
   			权限分配
   			<s:select onchange="selectRole()" id="selectRole" name="roles" list="%{#request.roleList}" listKey="roleId" listValue="rolename"></s:select>
   			<!--  
			<select name="" id="selectRole" style="width:100px" onchange="selectRole()">
    <option value=""></option>
    <option value="005">超级管理员</option>
    <option value="004">系统管理员</option>
    <option value="003">经理</option>
    <option value="002">项目组长</option>
    <option value="001">普通员工</option>
	</select>
	-->

	</legend>
	<legend align="right">
			<button onclick="updateRole()">保存修改</button>
	</legend>
 
     <table cellSpacing="0" cellPadding="0"border="0">
     
     		<s:iterator value="%{#request.functionMap}"  var="entry">
     			<tr>
     				<td class="ta_01"  align="right" width="20%"  style="font-weight: bold">
     					<s:property value="%{#entry.key}"/>
     				</td>
     				<td class="ta_01" align="left" width="90%"  >
     					<s:checkboxlist id="functions" name="functions" list="%{#entry.value}"   listKey="functionId" listValue="functionName"  ></s:checkboxlist>
     				</td>
     			</tr>
     		</s:iterator>
				 </table>	
		 </fieldset>
		 
		  <br/>
        
         <fieldset style="width:100%; border : 1px solid #73C8F9;text-align:left;color:#023726;font-size: 12px;min-height: 40%">
			   	<legend align="left">
			   			角色分配
						<input id="username" type="text"  size="15" name="username" />
						<button onclick="queryUser()">查询用户名</button>
				</legend>
			
			     <table id="userRoleTable" cellSpacing="0" cellPadding="0"border="0">
				 </table>	
		 </fieldset>
</div>
</div>		    				    
	</body>
</HTML>
