
/**
 * @(#)User.java     2014-06-23
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.dao.entity.system;

import com.kingsoft.control.util.StringManage;
import com.kingsoft.control.exception.DataBaseException;
import com.kingsoft.control.database.MappingTableModel;

/**
 * 用户信息
 * 
 * @author kingsoft
 * 
 * @version 2014-06-23
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public class User implements MappingTableModel {

	private static final long serialVersionUID = 1L;

	private static final String $MAPPING_TABLE_NAME = "sys_user";// 数据库表名称

	protected int count = 0;// 分页列表中的序号
	protected int id = Integer.MIN_VALUE;// 用户编号
	protected String loginId = StringManage.FS_EMPTY;// 登陆用户名
	protected String password = StringManage.FS_EMPTY;// 用户登陆密码
	protected int roleId = 0;// 角色编号
	protected int level = 0;// 操作级别(0:普通;1:中等;2:高级;)
	protected String cnName = StringManage.FS_EMPTY;// 用户中文名称
	protected String enName = StringManage.FS_EMPTY;// 用户英文名称
	protected int sex = 1;// 性别(0:女;1:男)
	protected String idCard = StringManage.FS_EMPTY;// 身份证号
	protected String address = StringManage.FS_EMPTY;// 住所地址
	protected String telephone = StringManage.FS_EMPTY;// 联系电话
	protected String mobileTelephone = StringManage.FS_EMPTY;// 联系手机
	protected String email = StringManage.FS_EMPTY;// 邮箱地址
	protected int actflag = 1;// 当前状态(0:停止使用;1:正常使用)
	protected String createTime = StringManage.FS_EMPTY;// 创建时间(格式:yyyy-MM-dd
	// HH:mm:ss)
	protected String loginTime = StringManage.FS_EMPTY;// 最后登陆时间(格式:yyyy-MM-dd

	// HH:mm:ss)

	public User() {

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		if (loginId != null) {
			this.loginId = loginId;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password != null) {
			this.password = password;
		}
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		if (cnName != null) {
			this.cnName = cnName;
		}
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		if (enName != null) {
			this.enName = enName;
		}
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		if (idCard != null) {
			this.idCard = idCard;
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if (address != null) {
			this.address = address;
		}
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		if (telephone != null) {
			this.telephone = telephone;
		}
	}

	public String getMobileTelephone() {
		return mobileTelephone;
	}

	public void setMobileTelephone(String mobileTelephone) {
		if (mobileTelephone != null) {
			this.mobileTelephone = mobileTelephone;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null) {
			this.email = email;
		}
	}

	public int getActflag() {
		return actflag;
	}

	public void setActflag(int actflag) {
		this.actflag = actflag;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		if (createTime != null) {
			this.createTime = createTime;
		}
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		if (loginTime != null) {
			this.loginTime = loginTime;
		}
	}

	public final String mappingTableName() {
		return $MAPPING_TABLE_NAME;
	}

	public void clone(MappingTableModel model) {
		if (!(model instanceof User)) {
			return;
		}
		User obj = (User) model;
		if (obj != null) {
			obj.count = count;
			obj.id = id;
			obj.loginId = loginId;
			obj.password = password;
			obj.roleId = roleId;
			obj.level = level;
			obj.cnName = cnName;
			obj.enName = enName;
			obj.sex = sex;
			obj.idCard = idCard;
			obj.address = address;
			obj.telephone = telephone;
			obj.mobileTelephone = mobileTelephone;
			obj.email = email;
			obj.actflag = actflag;
			obj.createTime = createTime;
			obj.loginTime = loginTime;
		}
	}

	public void validate() throws DataBaseException {
		if (StringManage.isEmpty(loginId)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"登陆用户名 不能为空.");
		}
		if (StringManage.isEmpty(createTime)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"创建时间(格式:yyyy-MM-dd HH:mm:ss) 不能为空.");
		}

	}
}


