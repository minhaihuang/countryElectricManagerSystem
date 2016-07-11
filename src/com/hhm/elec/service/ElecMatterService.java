package com.hhm.elec.service;

import java.util.List;

import com.hhm.elec.dao.IElecMatterDao;
import com.hhm.elec.domain.ElecMatter;

public class ElecMatterService {
	private IElecMatterDao elecMatterDao = null;

	public IElecMatterDao getElecMatterDao() {
		return elecMatterDao;
	}

	public void setElecMatterDao(IElecMatterDao elecMatterDao) {
		this.elecMatterDao = elecMatterDao;
	}

	/**
	 * 加入新的待办事宜的方法，加入之前会先把数据库中的所有待办事宜先删除，再加入新的，确保数据库中只有一条待办事宜。
	 * 
	 * @param elecMatter
	 */
	public void addElecMatter(ElecMatter elecMatter) {
		// 删除旧的内容
		List<ElecMatter> matters = this.elecMatterDao.getAll();

		if (matters != null && matters.size() != 0) {

			ElecMatter matter = matters.get(0);
			//更新数据
			matter.setDevRunStatus(elecMatter.getDevRunStatus());
			matter.setStationRunStatus(elecMatter.getStationRunStatus());
			matter.setCreateDate(elecMatter.getCreateDate());

			this.elecMatterDao.addOrUpdate(matter);
		}

	}

	/**
	 * 获取数据库中的待办事宜
	 * 
	 * @return
	 */
	public ElecMatter getElecMatter() {
		List<ElecMatter> matters = this.elecMatterDao.getAll();

		if (matters != null) {
			return matters.get(0);
		}
		return null;
	}

}
