package com.hhm.elec.domain;

import java.util.Set;

/**
 * 角色类，一个角色可对应多个权限
 * 
 * @author 黄帅哥
 * 
 */
public class ElecRole {
	/*
	 * #角色表 elecrole create table elecRole( roleId varchar(100) primary key,
	 * rolename varchar(50) unique#角色名称 );
	 */
	private String roleId;
	private String rolename;

	private Set<ElecFunction> functions = null;// 权限集合，一个角色可对应多个权限

	public Set<ElecFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<ElecFunction> functions) {
		this.functions = functions;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

}
