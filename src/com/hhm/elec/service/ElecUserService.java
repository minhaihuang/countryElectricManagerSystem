package com.hhm.elec.service;

import java.util.List;

import com.hhm.elec.dao.IElecUserDao;
import com.hhm.elec.domain.ElecUser;
import com.hhm.elec.util.Conditions;
import com.hhm.elec.util.Conditions.Operator;
import com.hhm.elec.util.DataTablesPage;

public class ElecUserService {
	private IElecUserDao elecUserDao=null;

	public IElecUserDao getElecUserDao() {
		return elecUserDao;
	}

	public void setElecUserDao(IElecUserDao elecUserDao) {
		this.elecUserDao = elecUserDao;
	}

	public void addUser(ElecUser elecUser) {
		this.elecUserDao.addOrUpdate(elecUser);
	}

	/**
	 * 检查账号是否唯一
	 * @param account
	 * @return
	 */
	public boolean checkAccountUnique(String account) {
		//条件查询
		Conditions conditions=new Conditions();
		conditions.addConditions("account", account, Operator.EQUAL);
		
		List<ElecUser> users=this.elecUserDao.geyByConditions(conditions);
		
		if(users!=null&&users.size()!=0){
			
			return false;//已经有了，不是唯一的
		}
		
		return true;
		
	}

	public void getPageData(DataTablesPage<ElecUser> page,Conditions conditions) {
		this.elecUserDao.getPageData(page,conditions);
	}

	/**
	 * 删除用户
	 * @param elecUser
	 */
	public void delete(ElecUser elecUser) {
		
		this.elecUserDao.deteteBean(elecUser);
	}

	/**
	 * 获取用户
	 * @param userId
	 * @return
	 */
	public ElecUser getElecUserByUserId(String userId) {
		
		return this.elecUserDao.getById(userId);
	}

	/**
	 * 更新用户
	 * @param elecUser
	 */
	public void updateUser(ElecUser elecUser) {
		this.elecUserDao.addOrUpdate(elecUser);
	}

	
	public List<ElecUser> findByConditions(Conditions conditions) {
		
		return this.elecUserDao.geyByConditions(conditions);
	}

	
}
