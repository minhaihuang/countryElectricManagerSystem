package com.hhm.elec.service;

import java.io.Serializable;

import com.hhm.elec.dao.IElecTextDao;
import com.hhm.elec.domain.ElecText;

/**
 * 这是service层
 * @author 黄帅哥
 *
 */
public class ElecTextService {
	private IElecTextDao elecTextDao=null;

	public IElecTextDao getElecTextDao() {
		return elecTextDao;
	}

	public void setElecTextDao(IElecTextDao elecTextDao) {
		this.elecTextDao = elecTextDao;
	}
	
	
	public void add(ElecText elecText){
		this.elecTextDao.addOrUpdate(elecText);
	}
	
	public ElecText getById(Serializable id){
		return this.elecTextDao.getById(id);
	}
}
