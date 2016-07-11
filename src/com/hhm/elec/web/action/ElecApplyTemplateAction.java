package com.hhm.elec.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;

import com.hhm.elec.domain.ElecApplyTemplate;
import com.hhm.elec.domain.ElecUser;
import com.hhm.elec.service.ElecJbpmService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ElecApplyTemplateAction extends ActionSupport implements
		ModelDriven<ElecApplyTemplate> {

	private ElecJbpmService elecJbpmService = null;
	private ElecApplyTemplate elecApplyTemplate = new ElecApplyTemplate();

	// 文件上传
	private File upload = null;
	private String uploadFileName = null;
	private String uploadContentType = null;

	// 文件下载
	private String id = null;
	private InputStream inputStream = null;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public ElecJbpmService getElecJbpmService() {
		return elecJbpmService;
	}

	public void setElecJbpmService(ElecJbpmService elecJbpmService) {
		this.elecJbpmService = elecJbpmService;
	}

	public ElecApplyTemplate getElecApplyTemplate() {
		return elecApplyTemplate;
	}

	public void setElecApplyTemplate(ElecApplyTemplate elecApplyTemplate) {
		this.elecApplyTemplate = elecApplyTemplate;
	}

	public ElecApplyTemplate getModel() {

		return elecApplyTemplate;
	}

	/**
	 * 导航到申请模板管理主页面
	 * 
	 * @return
	 */
	public String applyTemplateHome() {
		// 获取全部的申请模板
		List<ElecApplyTemplate> elecApplyTemplateList = this.elecJbpmService
				.getAllBean();

		ServletActionContext.getRequest().setAttribute("elecApplyTemplateList",
				elecApplyTemplateList);

		return "applyTemplateHome";
	}

	/**
	 * 导航到添加申请模板的页面
	 * 
	 * @return
	 */
	public String applyTemplateAdd() {
		// 获得最新的流程定义
		List<ProcessDefinition> definitionList = this.elecJbpmService
				.listNewestProcessDefinition();
		ServletActionContext.getRequest().setAttribute("definitionList",
				definitionList);
		return "applyTemplateAdd";
	}

	/**
	 * 文件上传
	 * 
	 * @return
	 */
	public String upload() {
		// 获取需要上传到的路径
		String path = ServletActionContext.getServletContext().getRealPath(
				"/upload/applyTemplate");

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		// 改名文件的名字，以免文件重复
		path += "/" + UUID.randomUUID().toString() + uploadFileName;// ydqwy9d8qwasshdo8sa员工请假申请模板.doc

		// 上传文件
		upload.renameTo(new File(path));

		// 封装数据
		elecApplyTemplate.setFileName(uploadFileName);
		elecApplyTemplate.setPath(path);

		System.out.println(path);
		System.out.println(elecApplyTemplate.getProcessDefinitionKey());
		// 更新或者添加数据
		this.elecJbpmService.addOrUpdateElecApplyTemplate(elecApplyTemplate);

		return "upload";
	}

	/**
	 * 文件下载
	 * 
	 * @return
	 */
	public String download() {
		ElecUser user = (ElecUser) ServletActionContext.getRequest()
				.getSession().getAttribute("elecUser");
		System.out.println(user.getAccount());
		// 1 根据templateId查询出ElecApplyTemplate对象
		elecApplyTemplate = elecJbpmService.getBeanById(id);

		try {
			inputStream = new FileInputStream(elecApplyTemplate.getPath());

			// 下载时，文件名默认为：{表单模板名称_account}.doc
			String filename = elecApplyTemplate.getFileName();
			int index = filename.lastIndexOf(".");// xx.doc ---- 2
			filename = filename.substring(0, index) + "_" + user.getAccount()
					+ filename.substring(index,filename.length());

			// 获取文件名称对应的文件mime类型
			String contentType = ServletActionContext.getServletContext()
					.getMimeType(elecApplyTemplate.getFileName());// xx.doc

			ServletActionContext.getRequest().setAttribute("contentType",
					contentType);
			ServletActionContext.getRequest().setAttribute("filename",
					URLEncoder.encode(filename, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return "download";
	}

	/**
	 * 删除申请
	 * 
	 * @return
	 */
	public String delete() {
		
		this.elecJbpmService.deleteById(id);
		return "delete";
	}

}
