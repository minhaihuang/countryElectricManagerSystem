package com.hhm.elec.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 这是测试类
 * @author 黄帅哥
 *
 */
public class ElecText implements Serializable{
	/*
	 * create table elecText(
	textId       varchar(100) primary key,
	textName     varchar(100)            ,
	textComment  text		     ,
	textDate     date		     

);
	 */
	private String textId;
	private String textName;
	private String textComment;
	private Date   textDate;
	public String getTextId() {
		return textId;
	}
	public void setTextId(String textId) {
		this.textId = textId;
	}
	public String getTextName() {
		return textName;
	}
	public void setTextName(String textName) {
		this.textName = textName;
	}
	public String getTextComment() {
		return textComment;
	}
	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}
	public Date getTextDate() {
		return textDate;
	}
	public void setTextDate(Date textDate) {
		this.textDate = textDate;
	}
	
	
}
