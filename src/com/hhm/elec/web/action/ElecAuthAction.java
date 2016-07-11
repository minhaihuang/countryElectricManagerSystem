package com.hhm.elec.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.hhm.elec.domain.ElecFunction;
import com.hhm.elec.domain.ElecRole;
import com.hhm.elec.domain.ElecUser;
import com.hhm.elec.service.ElecFunctionService;
import com.hhm.elec.service.ElecRoleService;
import com.hhm.elec.service.ElecUserService;
import com.hhm.elec.util.Conditions;
import com.hhm.elec.util.Conditions.Operator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 这是权限管理的action
 * 
 * @author 黄帅哥
 * 
 */
public class ElecAuthAction extends ActionSupport {
	private ElecUserService elecUserService = null;
	private ElecFunctionService elecFunctionService = null;
	private ElecRoleService elecRoleService = null;
	
	private String roleId=null;//角色的id，用于获取角色的权限
	private String functionIds=null;//所有选择的权限，用于更新权限
	private String username=null;//用户名，用于改变用户角色时的用户查找
	
	private String roles=null;//更新用户的角色时使用
	private String userId=null;//用户的id
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public ElecUserService getElecUserService() {
		return elecUserService;
	}

	public void setElecUserService(ElecUserService elecUserService) {
		this.elecUserService = elecUserService;
	}

	public ElecFunctionService getElecFunctionService() {
		return elecFunctionService;
	}

	public void setElecFunctionService(ElecFunctionService elecFunctionService) {
		this.elecFunctionService = elecFunctionService;
	}

	public ElecRoleService getElecRoleService() {
		return elecRoleService;
	}

	public void setElecRoleService(ElecRoleService elecRoleService) {
		this.elecRoleService = elecRoleService;
	}

	/**
	 * 用来导航到角色管理主页面
	 * @return
	 */
	public String roleHome(){
		//获取所有的角色
		List<ElecRole> roleList=this.elecRoleService.getAllRole();
		ServletActionContext.getRequest().setAttribute("roleList", roleList);
		
		//获取所有的权限
		List<ElecFunction> functionList=this.elecFunctionService.getAllFunction();
		//根据权限名称分组
		Map<String, List<ElecFunction>> functionMap=new HashMap<String, List<ElecFunction>>();
		if(functionList!=null&&functionList.size()!=0){
			for (ElecFunction elecFunction : functionList) {
				//获取组名
				String groups=elecFunction.getGroups();
				List<ElecFunction> tempList=functionMap.get(groups);
				if(tempList==null){
					tempList=new ArrayList<ElecFunction>();
				}
				tempList.add(elecFunction);
				functionMap.put(groups, tempList);
			}
		}
		ServletActionContext.getRequest().setAttribute("functionMap", functionMap);
		
		// 准备所有的角色的信息(json格式的数据)
		Gson gson = new Gson();
		String roleListJson = gson.toJson(roleList);
		ServletActionContext.getRequest().setAttribute("roleListJson", roleListJson);
		return "roleHome";
	}
	
	/**
	 * 获取角色的权限
	 * @return
	 * @throws IOException 
	 */
	public String selectRole() throws IOException{
		//获取角色
		ElecRole elecRole=this.elecRoleService.getRoleById(roleId);
		//获取角色的所有权限
		Set<ElecFunction> functions=elecRole.getFunctions();
		
		//遍历容器，用stringBuilder来装权限
		if(functions!=null&&functions.size()!=0){
		StringBuilder sb=new StringBuilder();
			for (ElecFunction elecFunction : functions) {
				sb.append(elecFunction.getFunctionId()).append(",");
			}
			//返回数据到客户端
		ServletActionContext.getResponse().getWriter().print(sb.toString());
		}
		
		return null;
	}
	
	/**
	 * 更新权限
	 * @return
	 * @throws IOException 
	 */
	public String updateRole() throws IOException{
		
		//根据角色的id获取到该角色
		ElecRole role=this.elecRoleService.getRoleById(roleId);
		
		//将所有权限的id组成的字符串切割
		String[] functions=functionIds.split(",");
		
		Set<ElecFunction> functionSet=new HashSet<ElecFunction>();
		//根据id获取相应的权限
		for (String functionId : functions) {
			ElecFunction elecFunction=this.elecFunctionService.getFunctionById(functionId);
			if(elecFunction!=null){
				functionSet.add(elecFunction);
			}
		}
		
		//更新角色的权限
		role.setFunctions(functionSet);
		this.elecRoleService.updateRole(role);
		
		ServletActionContext.getResponse().getWriter().print("succeed");
		return null;
	}
	
	
	/**
	 * 用于改变用户角色时的用户查找
	 * @return
	 * @throws IOException
	 */
	public String queryUser() throws IOException {
		Conditions conditions = new Conditions();
		conditions.addConditions("username", username, Operator.LIKE);

		List<ElecUser> userList = elecUserService.findByConditions(conditions);
		

		// 以哪种格式返回查到的数据
		Gson gson = new Gson();
		String userListJson = gson.toJson(userList);

		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(userListJson);

		return null;
	}

	/**
	 * 更新用户的角色
	 * @return
	 * @throws IOException 
	 */
	public String updateUserRole() throws IOException{
		//根据用户Id找到该用户
		ElecUser elecUser=this.elecUserService.getElecUserByUserId(userId);
		
		//切割角色id组成的字符串，获取各个角色
		String[] newRoles=roles.split(",");
		List<ElecRole> roleList=this.elecRoleService.getRoleList(newRoles);
		
		Set<ElecRole> roleSet=new HashSet<ElecRole>(roleList);
		//更新角色
		elecUser.setRoles(roleSet);
		this.elecUserService.updateUser(elecUser);
		
		//返回提醒
		ServletActionContext.getResponse().getWriter().print("succeed");
		return null;
	}
}
