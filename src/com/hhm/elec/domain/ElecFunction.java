package com.hhm.elec.domain;
/**
 * 权限类
 * @author 黄帅哥
 *
 */
public class ElecFunction {
	
	/*
	 * #权限表elecFunction create table elecFunction( functionId varchar(100)
	 * primary key, path varchar(255) unique,#请求的action的路径 functionName
	 * varchar(50) not null, groups varchar(50)#分组 );
	 */
	private String functionId;
	private String path;
	private String functionName;
	private String groups;

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

}
