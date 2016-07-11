package com.hhm.elec.web.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;

import com.hhm.elec.domain.ElecApply;
import com.hhm.elec.domain.ElecApplyTemplate;
import com.hhm.elec.domain.ElecUser;
import com.hhm.elec.service.ElecJbpmService;
import com.hhm.elec.util.Conditions;
import com.hhm.elec.util.Conditions.Operator;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import com.hhm.elec.domain.helper.PDefinitionATemplate;

public class ElecApplyAction extends ActionSupport implements
		ModelDriven<ElecApply> {

	private ElecApply elecApply = new ElecApply();
	private ElecJbpmService elecJbpmService = null;

	// 文件上传
	private File upload = null;
	private String uploadFileName = null;
	private String uploadContentType = null;

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

	public ElecApply getElecApply() {
		return elecApply;
	}

	public void setElecApply(ElecApply elecApply) {
		this.elecApply = elecApply;
	}

	public ElecJbpmService getElecJbpmService() {
		return elecJbpmService;
	}

	public void setElecJbpmService(ElecJbpmService elecJbpmService) {
		this.elecJbpmService = elecJbpmService;
	}

	public ElecApply getModel() {

		return elecApply;
	}

	/**
	 * 导航到起草申请页面
	 * 
	 * @return
	 */
	public String applyHome() {

		// 获取所有的流程定义
		List<ProcessDefinition> processDefinitions = this.elecJbpmService
				.listNewestProcessDefinition();

		// 获取所有的申请模板
		List<ElecApplyTemplate> elecApplyTemplates = this.elecJbpmService
				.getAllBean();

		// 利用辅助javabean
		List<PDefinitionATemplate> pdatList = new ArrayList<PDefinitionATemplate>();
		// 遍历
		for (ProcessDefinition processDefinition : processDefinitions) {

			String processDefinitionKey = processDefinition.getKey();
			String processDefinitionId = processDefinition.getId();

			String fileName = null;
			String templateId = null;
			String path = null;

			for (ElecApplyTemplate elecApplyTemplate : elecApplyTemplates) {

				// 若key相同
				if (processDefinitionKey.equals(elecApplyTemplate
						.getProcessDefinitionKey())) {
					fileName = elecApplyTemplate.getFileName();
					templateId = elecApplyTemplate.getTemplateId();
					path = elecApplyTemplate.getPath();

					break;
				}
			}

			PDefinitionATemplate pa = new PDefinitionATemplate();
			pa.setFileName(fileName);
			pa.setPath(path);
			pa.setProcessDefinitionId(processDefinitionId);
			pa.setProcessDefinitionKey(processDefinitionKey);
			pa.setTemplateId(templateId);
			pdatList.add(pa);

		}

		ServletActionContext.getRequest().setAttribute("pdatList", pdatList);
		return "applyHome";
	}

	/**
	 * 启动申请
	 * 
	 * @return
	 */
	public String startApply() {
		// 获取登陆的用户
		ElecUser elecUser = (ElecUser) ServletActionContext.getRequest()
				.getSession().getAttribute("elecUser");

		// 确定文件上传的路径
		String path = ServletActionContext.getServletContext().getRealPath(
				"/upload/applyFile");

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();// 如不存在，连父文件夹一起创建
		}
		// 避免文件名重复
		path += "/" + UUID.randomUUID().toString() + uploadFileName;
		// 上传文件
		upload.renameTo(new File(path));
		
		// 完善申请文件信息
		elecApply.setAccount(elecUser.getAccount());
		elecApply.setApplyTime(new Date());
		elecApply.setFileName(uploadFileName);
		elecApply.setPath(path);
		elecApply.setApplyStatus("applyStatus_ing");
		elecApply.setUserId(elecUser.getUserId());
		elecApply.setUsername(elecUser.getUsername());

		// 上传申请文件并且开启流程
		this.elecJbpmService.addApplyAndStartProcess(elecApply);

		return "startApply";
	}

	/**
	 * 导航到我的申请页面
	 * 
	 * @return
	 */
	public String applyMy() {

		// 根据条件获得本人的所有申请
		ElecUser elecUser = (ElecUser) ServletActionContext.getRequest()
				.getSession().getAttribute("elecUser");

		// 添加条件
		Conditions conditions = new Conditions();

		conditions.addConditions("account", elecUser.getAccount(),
				Operator.EQUAL);
		
		String processDefinitionKey=elecApply.getProcessDefinitionKey();
		String applyStatus=elecApply.getApplyStatus();
		
		//条件判断
		if(processDefinitionKey!=null&&processDefinitionKey.trim().length()!=0){
			conditions.addConditions("processDefinitionKey",
					processDefinitionKey, Operator.EQUAL);
		}
		
		if(applyStatus!=null&&applyStatus.trim().length()!=0){
			conditions.addConditions("applyStatus", applyStatus,
					Operator.EQUAL);
		}
		// 获取申请
		List<ElecApply> elecApplyList = this.elecJbpmService
				.getBeansByConditions(conditions);
		// 获取全部的流程定义
		List<ProcessDefinition> processDefinitionList = this.elecJbpmService
				.getDefinitionList();

		//设置到requestweb域中
		ServletActionContext.getRequest().setAttribute("elecApplyList",elecApplyList);
		ServletActionContext.getRequest().setAttribute("processDefinitionList", processDefinitionList);
		
		return "applyMy";
	}
}
