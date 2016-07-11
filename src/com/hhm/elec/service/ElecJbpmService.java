package com.hhm.elec.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;

import com.hhm.elec.dao.IElecApplyDao;
import com.hhm.elec.dao.IElecApplyTemplateDao;
import com.hhm.elec.dao.IElecUserDao;
import com.hhm.elec.domain.ElecApply;
import com.hhm.elec.domain.ElecApplyTemplate;
import com.hhm.elec.domain.ElecRole;
import com.hhm.elec.domain.ElecUser;
import com.hhm.elec.domain.helper.ApproveInfo;
import com.hhm.elec.domain.helper.TaskApply;
import com.hhm.elec.util.Conditions;
import com.hhm.elec.util.Conditions.Operator;

public class ElecJbpmService {
	// 获取流程引擎
	private ProcessEngine processEngine = null;
	// 申请模板
	private IElecApplyTemplateDao elecApplyTemplateDao = null;

	// 申请
	private IElecApplyDao elecApplyDao = null;
	private IElecUserDao elecUserDao = null;

	public IElecUserDao getElecUserDao() {
		return elecUserDao;
	}

	public void setElecUserDao(IElecUserDao elecUserDao) {
		this.elecUserDao = elecUserDao;
	}

	public IElecApplyDao getElecApplyDao() {
		return elecApplyDao;
	}

	public void setElecApplyDao(IElecApplyDao elecApplyDao) {
		this.elecApplyDao = elecApplyDao;
	}

	public IElecApplyTemplateDao getElecApplyTemplateDao() {
		return elecApplyTemplateDao;
	}

	public void setElecApplyTemplateDao(
			IElecApplyTemplateDao elecApplyTemplateDao) {
		this.elecApplyTemplateDao = elecApplyTemplateDao;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	/**
	 * 文件上传，部署流程定义
	 * 
	 * @param upload
	 */
	public void fileUpload(File upload) {
		// 获取部署对象
		NewDeployment newDeployment = this.processEngine.getRepositoryService()
				.createDeployment();

		// 加入部署文件
		try {
			newDeployment.addResourcesFromZipInputStream(new ZipInputStream(
					new FileInputStream(upload)));

			newDeployment.deploy();// 开始部署
		} catch (FileNotFoundException e) {

			throw new RuntimeException("部署文件失败" + e);
		}
	}

	/**
	 * 获取所有的流程定义
	 * 
	 * @return
	 */
	public List<ProcessDefinition> getDefinitionList() {
		ProcessDefinitionQuery processDefinitionQuery = this.processEngine
				.getRepositoryService().createProcessDefinitionQuery();
		List<ProcessDefinition> definitions = processDefinitionQuery.list();

		return definitions;
	}

	/**
	 * 查看流程图片
	 * 
	 * @param id
	 * @return
	 */
	public InputStream showProcessImage(String id) {
		// 获取查询工具对象
		ProcessDefinitionQuery processDefinitionQuery = this.processEngine
				.getRepositoryService().createProcessDefinitionQuery();

		ProcessDefinition processDefinition = processDefinitionQuery
				.processDefinitionId(id).uniqueResult();

		if (processDefinition != null) {
			return this.processEngine.getRepositoryService()
					.getResourceAsStream(processDefinition.getDeploymentId(),
							processDefinition.getImageResourceName());
		}
		return null;
	}

	/**
	 * 删除流程定义，即删除部署任务
	 * 
	 * @param id
	 */
	public void delete(String id) {
		// 获取查询工具对象
		ProcessDefinitionQuery processDefinitionQuery = this.processEngine
				.getRepositoryService().createProcessDefinitionQuery();

		ProcessDefinition processDefinition = processDefinitionQuery
				.processDefinitionId(id).uniqueResult();

		if (processDefinition != null) {
			this.processEngine.getRepositoryService().deleteDeployment(
					processDefinition.getDeploymentId());
		}
	}

	/**
	 * 查询全部流程的最新版本的流程定义
	 * 
	 * @return
	 */
	public List<ProcessDefinition> listNewestProcessDefinition() {

		ProcessDefinitionQuery processDefinitionQuery = processEngine
				.getRepositoryService().createProcessDefinitionQuery();

		List<ProcessDefinition> list = processDefinitionQuery.orderAsc(
				ProcessDefinitionQuery.PROPERTY_KEY).list();

		// 筛选出最新版本
		Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		if (list != null) {
			for (ProcessDefinition processDefinition : list) {

				map.put(processDefinition.getKey(), processDefinition);
			}
		}

		return new ArrayList<ProcessDefinition>(map.values());

	}

	/**
	 * 更新或者添加
	 * 
	 * @param elecApplyTemplate
	 */
	public void addOrUpdateElecApplyTemplate(ElecApplyTemplate elecApplyTemplate) {

		// 根据流程定义的key , 清除旧的模板数据
		Conditions conditions = new Conditions();
		conditions.addConditions("processDefinitionKey",
				elecApplyTemplate.getProcessDefinitionKey(), Operator.EQUAL);

		List<ElecApplyTemplate> oddList = elecApplyTemplateDao
				.geyByConditions(conditions);

		if (oddList != null && oddList.size() > 0) {
			elecApplyTemplateDao.deleteBeansAll(oddList);
		}
		this.elecApplyTemplateDao.addOrUpdate(elecApplyTemplate);
	}

	/**
	 * 获取全部的javabean
	 * 
	 * @return
	 */
	public <T> List<T> getAllBean() {
		return (List<T>) this.elecApplyTemplateDao.getAll();
	}

	/**
	 * 获取bean对象
	 * 
	 * @param id
	 * @return
	 */
	public <T> T getBeanById(String id) {
		return (T) this.elecApplyTemplateDao.getById(id);
	}

	// 根据id来删除对象
	public void deleteById(String id) {
		this.elecApplyTemplateDao.deleteBeanById(id);
	}

	/**
	 * 上传申请文件并且开启流程，并且完成第一个任务
	 * 
	 * @param elecApply
	 */
	public void addApplyAndStartProcess(ElecApply elecApply) {
		// 上传
		this.elecApplyDao.addOrUpdate(elecApply);

		// 开启流程
		Map<String, Object> varMap = new HashMap<String, Object>();
		varMap.put("account", elecApply.getAccount());
		// 关联当前申请信息和流程实例信息
		varMap.put("apply", elecApply);
		ExecutionService executionService = this.processEngine
				.getExecutionService();
		// 启动流程实例
		ProcessInstance processInstance = executionService
				.startProcessInstanceById(elecApply.getProcessDefinitionId(),
						varMap);

		// 完成第一个任务
		Task task = processEngine.getTaskService().createTaskQuery()
				.processInstanceId(processInstance.getId()).uniqueResult();
		processEngine.getTaskService().completeTask(task.getId());
	}

	/**
	 * 根据条件获取
	 * 
	 * @param conditions
	 * @return
	 */
	public <T> List<T> getBeansByConditions(Conditions conditions) {

		return (List<T>) this.elecApplyDao.geyByConditions(conditions);
	}

	/**
	 * 获取与用户相关的所有任务
	 * 
	 * @param elecUser
	 * @return
	 */
	public List<Task> getUserTask(ElecUser elecUser) {
		System.out.println(elecUser.getAccount());
		System.out.println(elecUser.getRoles());

		List<Task> allTaskList = new ArrayList<Task>();
		// 获取用户的所有角色
		Set<ElecRole> roles = elecUser.getRoles();

		// 寻找与角色相关的所有任务
		for (ElecRole elecRole : roles) {
			String roleName = elecRole.getRolename();

			List<Task> roleTasks = this.processEngine.getTaskService()
					.findPersonalTasks(roleName);

			if (roleTasks != null) {
				allTaskList.addAll(roleTasks);
			}
		}

		return allTaskList;
	}

	/**
	 * 返回带用户审批的所有任务，并且封装数据
	 * 
	 * @param elecUser
	 * @return
	 */
	public List<TaskApply> getTaskApplyByUser(ElecUser elecUser) {
		List<Task> allTaskList = getUserTask(elecUser);

		List<TaskApply> taskApplyList = new ArrayList<TaskApply>();

		// 封装数据
		for (Task task : allTaskList) {
			// 获取哪个任务的哪个变量
			ElecApply elecApply = (ElecApply) processEngine.getTaskService()
					.getVariable(task.getId(), "apply");

			TaskApply taskApply = new TaskApply();

			taskApply.setAccount(elecApply.getAccount());
			taskApply
					.setProcessDefinitionId(elecApply.getProcessDefinitionId());
			taskApply.setProcessDefinitionKey(elecApply
					.getProcessDefinitionKey());
			taskApply.setTaskId(task.getId());
			taskApply.setTaskName(task.getName());
			taskApply.setUserId(elecApply.getUserId());
			taskApply.setUserName(elecApply.getUsername());
			taskApply.setApplyId(elecApply.getApplyId());
			taskApplyList.add(taskApply);
		}
		return taskApplyList;
	}

	/**
	 * 通过任务的id来获取任务申请信息
	 * 
	 * @param taskId
	 * @return
	 */
	public TaskApply getTaskApplyByTaskId(String taskId) {

		// 获取该任务
		Task task = this.processEngine.getTaskService().getTask(taskId);

		// 获取该任务的相关信息
		ElecApply elecApply = (ElecApply) this.processEngine.getTaskService()
				.getVariable(taskId, "apply");

		// 获取下一步的操作
		Set<String> outComes = processEngine.getTaskService().getOutcomes(
				taskId);

		// 组装数据
		TaskApply taskApply = new TaskApply();

		taskApply.setAccount(elecApply.getAccount());
		taskApply.setApplyId(elecApply.getApplyId());
		taskApply.setFileName(elecApply.getFileName());
		taskApply.setPath(elecApply.getPath());
		taskApply.setProcessDefinitionId(elecApply.getProcessDefinitionId());
		taskApply.setProcessDefinitionKey(elecApply.getProcessDefinitionKey());
		taskApply.setTaskId(taskId);
		taskApply.setTaskName(task.getName());
		taskApply.setUserId(elecApply.getUserId());
		taskApply.setUserName(elecApply.getUsername());
		taskApply.setFileName(elecApply.getFileName());

		taskApply.setOutComes(outComes);

		return taskApply;
	}

	/**
	 * 审批审批
	 * 
	 * @param taskId
	 * @param isAgree
	 * @param outCome
	 * @param comment
	 */
	public void approve(ElecUser elecUser, String taskId, boolean isAgree,
			String outCome, String comment) {
		// 获取当前的申请
		ElecApply elecApply = (ElecApply) this.processEngine.getTaskService()
				.getVariable(taskId, "apply");

		// 获取执行任务的id(executionId)
		String executionId = this.processEngine.getTaskService()
				.getTask(taskId).getExecutionId();

		// 获取执行任务
		Execution execution = this.processEngine.getExecutionService()
				.findExecutionById(executionId);

		// 获取流程Id
		String processInstanceId = execution.getProcessInstance().getId();

		// 如果同意
		if (isAgree) {
			// 将审批意见放置进入容器
			Map<String, Object> varMap = new HashMap<String, Object>();

			// 获取当前任务
			Task task = this.processEngine.getTaskService().getTask(taskId);

			// 封装查看流程详情的信息
			ApproveInfo approveInfo = new ApproveInfo();
			approveInfo.setTaskId(taskId);
			approveInfo.setApplyResult("同意");
			approveInfo.setComment(comment);
			approveInfo.setTaskName(task.getName());
			approveInfo.setUserName(elecUser.getUsername());

			// 试着拿到一起设置的审批信息map
			Map<String, ApproveInfo> approveInfoMap = (Map<String, ApproveInfo>) processEngine
					.getTaskService().getVariable(taskId, "approveInfoMap");
			if (approveInfoMap == null) {
				System.out.println(1234);
				approveInfoMap = new HashMap<String, ApproveInfo>();
			}
			// 把本次审批的信息放入map
			approveInfoMap.put(taskId, approveInfo);
		

			varMap.put("comment", comment);
			varMap.put("approveInfoMap", approveInfoMap);

			// 完成当前任务
			this.processEngine.getTaskService().completeTask(taskId, outCome,
					varMap);

			// 获取流程实例
			ProcessInstance processInstance = this.processEngine
					.getExecutionService().findProcessInstanceById(
							processInstanceId);

			if (processInstance == null) {
				// 设置申请的状态
				elecApply.setApplyStatus("applyStatus_pass");
				this.elecApplyDao.addOrUpdate(elecApply);
			}
		} else {
			// 强制结束当前的流程实例
			this.processEngine.getExecutionService().endProcessInstance(
					processInstanceId, ProcessInstance.STATE_ENDED);
			// 更新申请状态
			// 设置申请的状态
			elecApply.setApplyStatus("applyStatus_notPass");
			this.elecApplyDao.addOrUpdate(elecApply);
		}

	}
	
	/**
	 * 获取流程详情
	 * @param taskId
	 * @return
	 */
	public Collection<ApproveInfo> listApproveInfo(String taskId) {

		Map<String, ApproveInfo> approveInfoMap = (Map<String, ApproveInfo>) processEngine.getTaskService().getVariable(taskId, "approveInfoMap");
		if (approveInfoMap != null) {
			return approveInfoMap.values();
		}
		return null;
	}

}
