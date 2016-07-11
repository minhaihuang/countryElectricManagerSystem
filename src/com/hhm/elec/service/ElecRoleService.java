package com.hhm.elec.service;

import java.util.ArrayList;
import java.util.List;

import com.hhm.elec.dao.IElecRoleDao;
import com.hhm.elec.domain.ElecRole;

/**
 * 这是ElecRole的service
 * @author 黄帅哥
 *
 */
public class ElecRoleService {
	private IElecRoleDao elecRoleDao=null;

	public IElecRoleDao getElecRoleDao() {
		return elecRoleDao;
	}

	public void setElecRoleDao(IElecRoleDao elecRoleDao) {
		this.elecRoleDao = elecRoleDao;
	}

	/**
	 * 获取所有的用户角色
	 * @return
	 */
	public List<ElecRole> getAllRole() {
		
		return this.elecRoleDao.getAll();
	}

	/**
	 * 根据角色的id找到该角色
	 * @param roleId
	 * @return
	 */
	public ElecRole getRoleById(String roleId) {
			
		return this.elecRoleDao.getById(roleId);
	}

	/**
	 * 更新角色权限
	 * @param role
	 */
	public void updateRole(ElecRole role) {
		this.elecRoleDao.addOrUpdate(role);
	}

	/**
	 * 根据id数组获取角色列表
	 * @param newRoles
	 * @return
	 */
	public List<ElecRole> getRoleList(String[] newRoles) {
		List<ElecRole> roleList=new ArrayList<ElecRole>();
		
		for (String roleId : newRoles) {
			ElecRole elecRole=getRoleById(roleId.trim());//有可能存在空格，去除空格
			if(elecRole!=null){
				roleList.add(elecRole);
			}
		}
		return roleList;
	}
	
	
}
