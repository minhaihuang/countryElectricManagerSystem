package com.hhm.elec.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.hhm.elec.domain.ElecApply;
import com.hhm.elec.domain.ElecUser;
import com.hhm.elec.domain.helper.ApproveInfo;
import com.hhm.elec.domain.helper.TaskApply;
import com.hhm.elec.service.ElecJbpmService;
import com.opensymphony.xwork2.ActionSupport;

public class ElecTaskAction extends ActionSupport {
	private ElecJbpmService elecJbpmService = null;

	private String taskId = null;

	private InputStream inputStream = null;
	
	private boolean isAgree=false;
	private String comment=null;
	
	private String outCome=null;

	public String getOutCome() {
		return outCome;
	}

	public void setOutCome(String outCome) {
		if(outCome!=null){
			try {
				outCome=new String(outCome.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		this.outCome = outCome;
	}

	public boolean getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(boolean isAgree) {
		this.isAgree = isAgree;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		if(comment!=null){
			try {
				comment=new String(comment.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		this.comment = comment;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getTaskId() {
		if(taskId!=null){
			try {
				taskId=new String(taskId.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public ElecJbpmService getElecJbpmService() {
		return elecJbpmService;
	}

	public void setElecJbpmService(ElecJbpmService elecJbpmService) {
		this.elecJbpmService = elecJbpmService;
	}

	/**
	 * 导航到待我审批主页面
	 * 
	 * @return
	 */
	public String taskMy() {
		// 获取登录的用户
		ElecUser elecUser = (ElecUser) ServletActionContext.getRequest()
				.getSession().getAttribute("elecUser");

		// 获取与该用户相关的所有申请任务
		List<TaskApply> taskApplyList = this.elecJbpmService
				.getTaskApplyByUser(elecUser);

		ServletActionContext.getRequest().setAttribute("taskApplyList",
				taskApplyList);
		return "taskMy";
	}

	/**
	 * 导航到审批申请页面
	 * 
	 * @return
	 */
	public String taskApprove() {

		TaskApply taskApply = this.elecJbpmService.getTaskApplyByTaskId(taskId);
		System.out.println(taskApply.getFileName());
		ServletActionContext.getRequest().setAttribute("taskApply", taskApply);
		return "taskApprove";
	}
	
	/**
	 * 文件下载
	 * @throws Exception 
	 */
	public String download() throws Exception{
		TaskApply taskApply = elecJbpmService.getTaskApplyByTaskId(taskId);
		inputStream = new FileInputStream(new File(taskApply.getPath()));

		String fileName = taskApply.getFileName();

		// 获取文件名称对应的文件mime类型
		String contentType = ServletActionContext.getServletContext().getMimeType(taskApply.getFileName());// xx.doc

		ServletActionContext.getRequest().setAttribute("contentType", contentType);
		ServletActionContext.getRequest().setAttribute("fileName", URLEncoder.encode(fileName, "utf-8"));
		return "download";
	}
	
	/**
	 * 审批
	 * @return
	 */
	public String approve(){
		ElecUser elecUser=(ElecUser) ServletActionContext.getRequest().getSession().getAttribute("elecUser");
		this.elecJbpmService.approve(elecUser,taskId,isAgree,outCome,comment);
		return "approve";
	}
	
	/**
	 * 查看申请流程详情页面
	 * @return
	 */
	public String processDetail(){

		Collection<ApproveInfo> approveInfos = elecJbpmService.listApproveInfo(taskId);

		ServletActionContext.getRequest().setAttribute("approveInfos", approveInfos);
		return "processDetail";
		
	}
}
