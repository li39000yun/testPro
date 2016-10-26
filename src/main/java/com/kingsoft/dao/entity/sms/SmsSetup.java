/**
 * @(#)SmsSetup.java     2011-01-22
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.dao.entity.sms;

import com.kingsoft.control.util.StringManage;
import com.kingsoft.control.exception.DataBaseException;
import com.kingsoft.control.database.MappingTableModel;

/**
 * 短信服务基础设置
 * 
 * @author kingsoft
 * 
 * @version 2011-01-22
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public class SmsSetup implements MappingTableModel {

	private static final long serialVersionUID = 1L;

	private static final String $MAPPING_TABLE_NAME = "sms_setup";// 数据库表名称

	protected int count = 0;// 分页列表中的序号
	protected int id = Integer.MIN_VALUE;// 流水号
	protected String telephone = StringManage.FS_EMPTY;// 本地号码
	protected String dbName = StringManage.FS_EMPTY;// 数据库名称
	protected String dbUser = StringManage.FS_EMPTY;// 据数库登陆名称
	protected String dbPass = StringManage.FS_EMPTY;// 据数库登陆密码
	protected String userId = StringManage.FS_EMPTY;// 用户名称
	protected String userPass = StringManage.FS_EMPTY;// 用户密码
	protected String serviceId = StringManage.FS_EMPTY;// 业务类型
	protected String feeType = StringManage.FS_EMPTY;// 资费类型
	protected String feeCode = StringManage.FS_EMPTY;// 资费代码
	protected String url = StringManage.FS_EMPTY;// webservices地址
	protected int actflag = 1;// 当前状态,(0:休眠;1:激活;)

	public SmsSetup() {

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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		if (telephone != null) {
			this.telephone = telephone;
		}
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		if (dbName != null) {
			this.dbName = dbName;
		}
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		if (dbUser != null) {
			this.dbUser = dbUser;
		}
	}

	public String getDbPass() {
		return dbPass;
	}

	public void setDbPass(String dbPass) {
		if (dbPass != null) {
			this.dbPass = dbPass;
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		if (userId != null) {
			this.userId = userId;
		}
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		if (userPass != null) {
			this.userPass = userPass;
		}
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		if (serviceId != null) {
			this.serviceId = serviceId;
		}
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		if (feeType != null) {
			this.feeType = feeType;
		}
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		if (feeCode != null) {
			this.feeCode = feeCode;
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if (url != null) {
			this.url = url;
		}
	}

	public int getActflag() {
		return actflag;
	}

	public void setActflag(int actflag) {
		this.actflag = actflag;
	}

	public final String mappingTableName() {
		return $MAPPING_TABLE_NAME;
	}

	public void clone(MappingTableModel model) {
		if (!(model instanceof SmsSetup)) {
			return;
		}
		SmsSetup obj = (SmsSetup) model;
		if (obj != null) {
			obj.count = count;
			obj.id = id;
			obj.telephone = telephone;
			obj.dbName = dbName;
			obj.dbUser = dbUser;
			obj.dbPass = dbPass;
			obj.userId = userId;
			obj.userPass = userPass;
			obj.serviceId = serviceId;
			obj.feeType = feeType;
			obj.feeCode = feeCode;
			obj.url = url;
			obj.actflag = actflag;
		}
	}

	public void validate() throws DataBaseException {

	}
}