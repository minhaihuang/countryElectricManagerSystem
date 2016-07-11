package com.hhm.elec.dao.impl;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.hhm.elec.dao.IDao;
import com.hhm.elec.util.Conditions;
import com.hhm.elec.util.Conditions.WhereSqlAndValue;
import com.hhm.elec.util.DataTablesPage;

public class DaoImpl<T> implements IDao<T> {

	// 操作数据库的模板对象HibernateTemplate
	private HibernateTemplate hibernateTemplate = null;
	// 找到对象所属的类型
	private Class beanClass;

	// 初始化获得对象的类型
	{
		// 参数化类型
		// IDao<ElecText>
		ParameterizedType paramType = (ParameterizedType) this.getClass()
				.getGenericSuperclass();

		Type[] argTypes = paramType.getActualTypeArguments();

		beanClass = (Class) argTypes[0];

	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * 加入对象
	 */
	public void addOrUpdate(Object bean) {
		this.hibernateTemplate.saveOrUpdate(bean);
	}

	/**
	 * 根据id查询对象
	 */
	public T getById(Serializable id) {

		return (T) this.hibernateTemplate.get(beanClass, id);
	}

	/*
	 * 更新或添加一个容器中全部的bean
	 * 
	 * @see com.hhm.elec.dao.IDao#addOrUpdateAll(java.util.Collection)
	 */
	public void addOrUpdateAll(Collection<T> beans) {
		if (beans != null && beans.size() != 0) {
			this.hibernateTemplate.saveOrUpdateAll(beans);
		}

	}

	/*
	 * 根据bean对象来删除对象
	 * 
	 * @see com.hhm.elec.dao.IDao#deteteBean(java.lang.Object)
	 */
	public void deteteBean(T bean) {
		this.hibernateTemplate.delete(bean);
	}

	/*
	 * 根据bean对象的id来删除bean对象
	 * 
	 * @see com.hhm.elec.dao.IDao#deleteBeanById(java.io.Serializable)
	 */
	public void deleteBeanById(Serializable id) {
		// 要先找到该对象
		T bean = getById(id);
		// 再删除
		this.hibernateTemplate.delete(bean);
	}

	/*
	 * 从数据库删除包含在该容器中的全部bean
	 * 
	 * @see com.hhm.elec.dao.IDao#deleteBeansAll(java.util.Collection)
	 */
	public void deleteBeansAll(Collection<T> beans) {
		this.hibernateTemplate.deleteAll(beans);
	}

	public List<T> geyByConditions(Conditions conditions) {
		// HQL
		String sql = "from " + beanClass.getName();
		String tempStr = conditions.createWhereSqlAndValue().getWhereSql();
		if (conditions.getOrderByList() != null
				&& conditions.getOrderByList().size() != 0) {
			sql += tempStr + conditions.createOrderByString();
		} else {
			sql += tempStr;
		}

		// values
		Object[] values = conditions.createWhereSqlAndValue().getValues();

		System.out.println(sql);
		System.out.println(Arrays.toString(values));
		return hibernateTemplate.find(sql, values);

	}

	public List<T> getAll() {
		String sql = "from " + beanClass.getName();

		return hibernateTemplate.find(sql);
	}

	public void getPageData(final DataTablesPage<T> page,
			Conditions conditions) {
		// 获取查询的条件
		WhereSqlAndValue wv = conditions.createWhereSqlAndValue();
		final String hql = "from " + beanClass.getName() + wv.getWhereSql();// hql语句
		final Object[] values = wv.getValues();// 占位符的值
		
		System.out.println(Arrays.toString(values));

		System.out.println(hql);

		// 获取数据
		List<T> data = this.hibernateTemplate
				.execute(new HibernateCallback<List<T>>() {

					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						// 准备查询语句。和对象
						Query query = session.createQuery(hql);
						// 设置占位符的值
						for (int i = 0; i < values.length; i++) {
							query.setParameter(i, values[i]);
						}

						// 确定开始的查询索引
						query.setFirstResult(page.getIDisplayStart());
						// 确定最大的查询索引
						query.setMaxResults(page.getIDisplayLength());
						return query.list();
					}

				});

		// 获取所有符合条件的数据的总数
		final String totalHql = "select count(*) from " + beanClass.getName() + wv.getWhereSql();// hql语句
		System.out.println(totalHql);
		long iTotalRecords  = this.hibernateTemplate
				.execute(new HibernateCallback<Long>() {

					public Long doInHibernate(Session session)
							throws HibernateException, SQLException {
						// 准备查询语句。和对象
						Query query = session.createQuery(totalHql);
						// 设置占位符的值
						for (int i = 0; i < values.length; i++) {
							query.setParameter(i, values[i]);
						}

						//获取查询总数并且返回
						System.out.println(totalHql);
						return (Long) query.uniqueResult();
					}

				});

		page.setData(data);
		page.setITotalRecords((int)iTotalRecords);
		page.setITotalDisplayRecords(page.getITotalRecords());
		
		System.out.println(data.toString());
		
	}

}
