package com.hhm.elec.domain;

import java.util.Date;
import java.util.Set;

/**
 * 这是用户的javaBean类
 * 
 * @author 黄帅哥
 * 
 */
public class ElecUser {
	private String userId;
	private String account;// 登录账号，指定唯一性
	private String password;// 密码
	private String username;// 用户名
	private String gender;// 性别
	private Date birthday;// 出生日期
	private String address;// 地址
	private String homeTel;// 家庭电话
	private String phone;// 手机号码
	private String email;// 电子邮箱
	private String isDuty;// 是否在职
	private String units;// 所属单位
	private Date onDutyDate;// 入职日期
	private Date offDutyDate;// 离职日期
	private String comment;// 备注

	// 为了保证数据安全;问责的机制
	private String createUser;// 创建者userId
	private Date createDate;// 创建日期
	private String lastUpdateUser;// 最后一次修改的userId;(其他修改记录使用历史表;或者记录日志)
	private Date lastUpdateDate;// 最后一次修改日期

	// 假删除;数据才是核心;没有了数据 ; 为了数据的恒久性
	private boolean isDelete;
	
	private Set<ElecRole> roles=null;//角色集合，一个用户可对应多个角色
	

	public Set<ElecRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<ElecRole> roles) {
		this.roles = roles;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsDuty() {
		return isDuty;
	}

	public void setIsDuty(String isDuty) {
		this.isDuty = isDuty;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public Date getOnDutyDate() {
		return onDutyDate;
	}

	public void setOnDutyDate(Date onDutyDate) {
		this.onDutyDate = onDutyDate;
	}

	public Date getOffDutyDate() {
		return offDutyDate;
	}

	public void setOffDutyDate(Date offDutyDate) {
		this.offDutyDate = offDutyDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	//注意此处，在自动生成时方法名称是 isDelete，这会导致编译无法通过，要手动改为getIsDelete
	public boolean getIsDelete() {
		return isDelete;
	}

	//注意此处，在自动生成时方法名称是setDelete，这会导致编译无法通过，要手动改为setIsDelete
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	
	
	
	
}
