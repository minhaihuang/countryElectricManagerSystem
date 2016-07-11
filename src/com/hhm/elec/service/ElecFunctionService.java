package com.hhm.elec.service;

import java.util.List;

import com.hhm.elec.dao.IElecFunctionDao;
import com.hhm.elec.domain.ElecFunction;
/**
 * 这是ElecFunction的service
 * @author 黄帅哥
 *
 */
public class ElecFunctionService {
	private IElecFunctionDao elecFunctionDao=null;

	public IElecFunctionDao getElecFunctionDao() {
		return elecFunctionDao;
	}

	public void setElecFunctionDao(IElecFunctionDao elecFunctionDao) {
		this.elecFunctionDao = elecFunctionDao;
	}

	/**
	 * 获取所有的权限
	 * @return
	 */
	public List<ElecFunction> getAllFunction() {
		
		return this.elecFunctionDao.getAll();
	}

	/**
	 * 根据id获取相应的权限
	 * @param functionId
	 * @return
	 */
	public ElecFunction getFunctionById(String functionId) {
		
		return this.elecFunctionDao.getById(functionId);
	}

	
	
	
}
