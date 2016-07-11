<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>添加用户</title>
<LINK href="${pageContext.request.contextPath }/css/Style.css"
	type="text/css" rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/DatePicker/WdatePicker.js">
	
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/jquery-1.8.3.js">
	
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/validate/validate.js">
	
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/script/validate/validateMessage_zh_CN.js">
	
</script>
</head>
<script type="text/javascript">
	//客户端检查数据有效性
	$().ready(function() {
		$("#form1").validate({
			rules : {
				account:{
					required:true,
				},
			
				username : {
					required : true,
				},

				password : {
					required : true,
					minlength : 3,
				},

				repassword : {
					required : true,
					minlength : 3,
					equalTo : "#password",
				},

				email : {
					required : true,
					email : true,
				}
			},
			submitHandler : function(form) {
				form.submit();
				alert('请求已提交');
				opener.location.reload();
				window.close();
			}

		}

		);
	});
</script>

<script type="text/javascript">
	//服务器端数据有效性检查，检查账号的唯一性，利用ajax
	function checkUnique(dom) {
		//获取参数
		var value = $(dom).val();
		if (value) {
			$
					.post(
							"${pageContext.request.contextPath }/system/elecUserAction_checkAccountUnique.action",
							{
								'account' : value
							},
							function(data) {
								var accountImg = $("#accountImg");
								if (data == 'true') {
									//如果账号是唯一的，显示对号小图标
									accountImg.css('display', 'inline');
									accountImg
											.attr("src",
													'${pageContext.request.contextPath }/images/right.png');
									$(dom).after(accountImg);
								} else {
									accountImg.removeAttr("src");
									alert("该登陆账号已经存在");
								}

							});
		}
		;
	}

</script>

<body>
	<br>
	<form id="form1"
		action="${pageContext.request.contextPath }/system/elecUserAction_addUser.action"
		method="post">
		<table cellSpacing="1" cellPadding="5" width="620px" align="center"
			bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
			<tr>
				<td class="ta_01" align="center" colSpan="4"
					background="${pageContext.request.contextPath }/images/b-info.gif">
					<font face="宋体" size="2"><strong>添加用户</strong>
				</font></td>
			</tr>

			<tr>
				<td align="center" bgColor="#f5fafe" class="ta_01">登录账号：<font
					color="#FF0000">*</font>
				</td>
				<td class="ta_01" bgColor="#ffffff"><input id='account'
					name="account" type="text" onblur="checkUnique(this)" size="20" /><img
					id="accountImg" style="display:none"></td>
				<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">用户姓名：<font
					color="#FF0000">*</font>
				</td>
				<td class="ta_01" bgColor="#ffffff"><input id="username"
					name="username" type="text" size="20"></td>
			</tr>
			<tr>
				<td align="center" bgColor="#f5fafe" class="ta_01">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
				<td class="ta_01" bgColor="#ffffff">
					<!-- 利用数据字典来引入数据 --> <s:radio name="gender"
						list="%{#application.dictionary.dictionaryMap.gender}"></s:radio>
				</td>
				<td align="center" bgColor="#f5fafe" class="ta_01">所属单位：<font
					color="#FF0000">*</font>
				</td>
				<td class="ta_01" bgColor="#ffffff">
					<!-- 利用数据字典来引入数据 --> <s:select name="units"
						list="%{#application.dictionary.dictionaryMap.units}"></s:select>
				</td>
			</tr>
			<tr>
				<td align="center" bgColor="#f5fafe" class="ta_01">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
				<td class="ta_01" bgColor="#ffffff"><input name="password"
					type="password" id="password"/></td>
				<td align="center" bgColor="#f5fafe" class="ta_01">确认密码：</td>
				<td class="ta_01" bgColor="#ffffff"><input name="repassword" id="repassword"
					type="password" /></td>
			</tr>

			<tr>
				<td align="center" bgColor="#f5fafe" class="ta_01">出生日期：</td>
				<td class="ta_01" bgColor="#ffffff"><input name="birthday"
					type="text" onclick="WdatePicker()" /></td>
				<td align="center" bgColor="#f5fafe" class="ta_01">联系地址：</td>
				<td class="ta_01" bgColor="#ffffff"><input name="address"
					type="text" /></td>
			</tr>

			<tr>
				<td align="center" bgColor="#f5fafe" class="ta_01">家庭电话：</td>
				<td class="ta_01" bgColor="#ffffff"><input name="homeTel"
					type="text" /></td>
				<td align="center" bgColor="#f5fafe" class="ta_01">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
				<td class="ta_01" bgColor="#ffffff"><input name="phone"
					type="text" /></td>
			</tr>

			<tr>
				<td align="center" bgColor="#f5fafe" class="ta_01">电子邮箱：</td>
				<td class="ta_01" bgColor="#ffffff"><input name="email"
					type="text" /></td>
				<td align="center" bgColor="#f5fafe" class="ta_01">是否在职：</td>
				<td class="ta_01" bgColor="#ffffff"><select name="isDuty"
					style="width:155px">
						<option value="1" selected>是</option>
						<option value="2">否</option>
				</select></td>
			</tr>

			<tr>
				<td align="center" bgColor="#f5fafe" class="ta_01">入职日期：</td>
				<td class="ta_01" bgColor="#ffffff"><input name="onDutyDate"
					type="text" onclick="WdatePicker()"></td>
				<td align="center" bgColor="#ffffff" class="ta_01"></td>
				<td class="ta_01" bgColor="#ffffff"></td>
			</tr>

			<tr>
				<td class="ta_01" align="center" bgColor="#f5fafe">
					备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
				<td class="ta_01" bgColor="#ffffff" colSpan="3"><textarea
						name="comment" style="WIDTH: 95%" rows="4" cols="52"></textarea></td>
			</tr>
			<tr>
				<td align="center" colSpan="4" class="sep1"></td>
			</tr>
			<tr>
				<td class="ta_01" style="WIDTH: 100%" align="center"
					bgColor="#f5fafe" colSpan="4"><input type="submit" value="添加"
					style="font-size: 12px; color: black;" /><font face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
					<input style="font-size: 12px; color: black;" type="button"
					value="关闭" name="Reset1" onClick="window.close()"></td>
			</tr>
		</table>
	</form>

</body>
</html>
