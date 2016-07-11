package com.hhm.elec.web.action;

import org.apache.struts2.ServletActionContext;

import com.hhm.elec.domain.ElecMatter;
import com.hhm.elec.service.ElecMatterService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 用来转发请求的Action
 * 
 * @author 黄帅哥
 * 
 */
public class MenuAction extends ActionSupport {
	
	private ElecMatterService elecMatterService=null;
	
	

	public ElecMatterService getElecMatterService() {
		return elecMatterService;
	}

	public void setElecMatterService(ElecMatterService elecMatterService) {
		this.elecMatterService = elecMatterService;
	}

	public String alermJX() {
		return "alermJX";
	}

	public String alermSB() {
		//获取待办事宜，设置到request中
		ElecMatter elecMatter=this.elecMatterService.getElecMatter();
		if(elecMatter!=null){
			ServletActionContext.getRequest().setAttribute("elecMatter", elecMatter);
		}
		
		return "alermSB";
	}

	public String alermXZ() {
		return "alermXZ";
	}

	public String alermYS() {
		return "alermYS";
	}

	public String alermZD() {
		//获取待办事宜，设置到request中
		ElecMatter elecMatter=this.elecMatterService.getElecMatter();
		if(elecMatter!=null){
			ServletActionContext.getRequest().setAttribute("elecMatter", elecMatter);
		}
		return "alermZD";
	}

	public String error() {
		return "error";
	}

	public String home() {
		return "home";
	}

	public String index() {
		return "index";
	}

	public String left() {
		return "left";
	}

	public String loading() {
		return "loading";
	}

	public String title() {
		return "title";
	}

}
