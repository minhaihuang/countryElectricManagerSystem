package com.hhm.elec.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.hhm.elec.domain.ElecUser;
import com.hhm.elec.util.Conditions;
import com.hhm.elec.util.DataTablesPage;


/**
 * 这是顶层Dao
 * @author 黄帅哥
 *
 */
public interface IDao<T> {
	//加入对象的通用方法
	public void addOrUpdate(T bean);
	
	public void  addOrUpdateAll(Collection<T> beans);
	
	public void deteteBean(T bean);
	
	public void deleteBeanById(Serializable id);
	
	public void deleteBeansAll(Collection<T> beans);
	

	
	//查询对象的通用方法
	public T getById(Serializable id);
	public List<T> geyByConditions(Conditions conditions);
	public List<T> getAll();
	
	//获取分页数据
	public void getPageData(DataTablesPage<T> page,Conditions conditions);
}
