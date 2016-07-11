package com.hhm.elec.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 这是代办事宜的javabean类
 * @author 黄帅哥
 *
 */
public class ElecMatter implements Serializable{
	/*create table elecMatter(
			matterId varchar(100) primary key,
			stationRunStatus text,#站点运行情况
			devRunStatus text,# 设备运行情况
			createDate date
		);
		*/
	private String matterId;
	private String stationRunStatus;
	private String devRunStatus;
	private Date createDate;
	public String getMatterId() {
		return matterId;
	}
	public void setMatterId(String matterId) {
		this.matterId = matterId;
	}
	public String getStationRunStatus() {
		return stationRunStatus;
	}
	public void setStationRunStatus(String stationRunStatus) {
		this.stationRunStatus = stationRunStatus;
	}
	public String getDevRunStatus() {
		return devRunStatus;
	}
	public void setDevRunStatus(String devRunStatus) {
		this.devRunStatus = devRunStatus;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
