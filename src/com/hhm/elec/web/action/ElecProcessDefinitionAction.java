package com.hhm.elec.web.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;

import com.hhm.elec.service.ElecJbpmService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 这是工作流程定义的action类
 * 
 * @author 黄帅哥
 * 
 */
public class ElecProcessDefinitionAction extends ActionSupport {
	private ElecJbpmService elecJbpmService = null;

	// 文件上传
	private File upload = null;
	private String uploadContentType;// 只能上传.zip格式的文件
	private String uploadFileName;
	
	//利用struts框架来实现文件下载
	private InputStream inputStream=null;
	private String id;// 流程定义对象的id
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		//处理乱码
		if (id != null) {
			try {
				id = new String(id.getBytes("ISO-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		this.id = id;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ElecJbpmService getElecJbpmService() {
		return elecJbpmService;
	}

	public void setElecJbpmService(ElecJbpmService elecJbpmService) {
		this.elecJbpmService = elecJbpmService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * 导航到流程审批管理主页
	 * 
	 * @return
	 */
	public String processDefinitionHome() {
		//获取流程定义的最新版本
		List<ProcessDefinition> definitions = this.elecJbpmService
				.getDefinitionList();
		//用map，来设置每个流程定义的最新版本
		Map<String,ProcessDefinition> processDefinitionMap=new HashMap<String,ProcessDefinition>();
		
		//遍历definitions，根据定义的key放置version进入map
		for (ProcessDefinition processDefinition : definitions) {
			processDefinitionMap.put(processDefinition.getKey(), processDefinition);
		}
		
		// 设置到request域中，map中现在存放的是每一个流程定义的最新版本
		ServletActionContext.getRequest().setAttribute("processDefinitionMap",
				processDefinitionMap);
		
		
		return "processDefinitionHome";
	}

	/**
	 * 用来导航到添加流程定义的页面
	 * 
	 * @return
	 */
	public String processDefinitionAdd() {
		return "processDefinitionAdd";
	}

	/**
	 * 文件上传
	 */

	public String deployment() {
		// 有效性判断
		if (upload != null) {
			System.out.println(123456555);
			this.elecJbpmService.fileUpload(upload);
		}

		return "deployment";
	}

	/**
	 * 查看流程图
	 * @return
	 */
	public String showProcessImg(){
		inputStream = elecJbpmService.showProcessImage(id);
		
		System.out.println(12345);
		return "showProcessImg";
	}
	
	/**
	 * 删除流程定义
	 * @return
	 */
	public String delete(){
		
		this.elecJbpmService.delete(id);
		return "delete";
	}
}
