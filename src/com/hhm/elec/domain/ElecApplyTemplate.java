package com.hhm.elec.domain;

import java.io.Serializable;

/**
 * 这是申请模板类
 * 
 * @author 黄帅哥
 * 
 */
public class ElecApplyTemplate implements Serializable {
	/**
	 * create table elecApplyTemplate( templateId varchar(100) primary key,
	 * #模板id filename varchar(100), #模板文件名 path text ,#模板文件真实路径
	 * processDefinitionKey varchar(100)#关联的流程定义的key );
	 */
	private String templateId;// #模板id
	private String fileName;// #模板文件名
	private String path;// #模板文件真实路径
	private String processDefinitionKey;// #关联的流程定义的key

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

}
