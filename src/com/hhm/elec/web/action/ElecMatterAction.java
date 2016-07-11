package com.hhm.elec.web.action;


import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.hhm.elec.domain.ElecMatter;
import com.hhm.elec.service.ElecMatterService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ElecMatterAction extends ActionSupport implements ModelDriven<ElecMatter>{
	//service对象
	private ElecMatterService elecMatterService=null;
	//ElecMatter对象
	private ElecMatter elecMatter=new ElecMatter();
	
	
	public ElecMatterService getElecMatterService() {
		return elecMatterService;
	}

	public void setElecMatterService(ElecMatterService elecMatterService) {
		this.elecMatterService = elecMatterService;
	}



	public ElecMatter getElecMatter() {
		return elecMatter;
	}


	public void setElecMatter(ElecMatter elecMatter) {
		this.elecMatter = elecMatter;
	}


	public ElecMatter getModel() {
		
		return elecMatter;
	}
	
	/**
	 * 用来跳转到待办事宜的主页面
	 * @return
	 */
	public String matterHome(){
		//获取待办事宜，设置到request中
		ElecMatter elecMatter=this.elecMatterService.getElecMatter();
		if(elecMatter!=null){
			ServletActionContext.getRequest().setAttribute("elecMatter", elecMatter);
		}
		return "matterHome";
	}
	
	/**
	 * 增加待办事宜的方法
	 * @return
	 */
	public String addElecMatter(){
		//注入当前的时间
		this.elecMatter.setCreateDate(new Date());
		
		elecMatterService.addElecMatter(elecMatter);
		return "addElecMatter";
	}

}
