package com.hhm.elec.domain;

import java.io.Serializable;
import java.util.Date;

public class ElecApply implements Serializable {
	/*
	 * create table elecApply( applyId varchar(100) primary key, userId
	 * varchar(100), //申请人的信息 username varchar(50), account varchar(50) ,
	 * processDefinitionId varchar(100) ,//记录提交申请时特定版本的流程定义 , 供审批时使用
	 * processDefinitionKey varchar(50), applyTime date, applyStatus varchar(50)
	 * , //申请状态 filename varchar(100),//申请文件的信息 path text );
	 */

	private String applyId = null;
	private String userId = null; // 申请人的信息
	private String username = null;
	private String account = null;
	private String processDefinitionId = null;// 记录提交申请时特定版本的流程定义 , 供审批时使用
	private String processDefinitionKey = null;
	private Date applyTime = null;
	private String applyStatus = null; // 申请状态
	private String fileName = null;// 申请文件的信息
	private String path = null;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
