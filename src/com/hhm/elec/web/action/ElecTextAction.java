package com.hhm.elec.web.action;

import com.hhm.elec.domain.ElecText;
import com.hhm.elec.service.ElecTextService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ElecTextAction extends ActionSupport implements
		ModelDriven<ElecText> {
	// 一定是要new
	private ElecText elecText = new ElecText();
	private ElecTextService elecTextService = null;

	public ElecText getElecText() {
		return elecText;
	}

	public void setElecText(ElecText elecText) {
		this.elecText = elecText;
	}

	public ElecTextService getElecTextService() {
		return elecTextService;
	}

	public void setElecTextService(ElecTextService elecTextService) {
		this.elecTextService = elecTextService;
	}

	public ElecText getModel() {

		return elecText;
	}

	
	public String addElecText(){
		
		//调用service层的方法加入数据库
		this.elecTextService.add(elecText);
		return "success";
	}
}
