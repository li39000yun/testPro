/**
 * @(#)ServiceCenter.java     2011-02-25
 * 
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 **/
package com.kingsoft.nb.dao.entity.manager;

import com.kingsoft.control.util.StringManage;
import com.kingsoft.control.exception.DataBaseException;
import com.kingsoft.control.database.MappingTableModel;

/**
 * 服务中心资料
 * 
 * @author kingsoft
 * 
 * @version 2011-02-25
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */

public class ServiceCenter implements MappingTableModel {

	private static final long serialVersionUID = 1L;

	private static final String $MAPPING_TABLE_NAME = "global_service_center";// 数据库表名称

	protected int count = 0;// 分页列表中的序号
	protected int id = Integer.MIN_VALUE;// 流水号
	protected String accreditId = StringManage.FS_EMPTY;// 授权编号
	protected String userId = StringManage.FS_EMPTY;// 用户名
	protected String password = StringManage.FS_EMPTY;// 密码
	protected String url = StringManage.FS_EMPTY;// 链接地址

	public ServiceCenter() {

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

	public String getAccreditId() {
		return accreditId;
	}

	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		if (userId != null) {
			this.userId = userId;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if (url != null) {
			this.url = url;
		}
	}

	public final String mappingTableName() {
		return $MAPPING_TABLE_NAME;
	}

	public void clone(MappingTableModel model) {
		if (!(model instanceof ServiceCenter)) {
			return;
		}
		ServiceCenter obj = (ServiceCenter) model;
		if (obj != null) {
			obj.count = count;
			obj.id = id;
			obj.accreditId = accreditId;
			obj.userId = userId;
			obj.password = password;
			obj.url = url;
		}
	}

	public void validate() throws DataBaseException {
		if (StringManage.isEmpty(userId)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"用户名 不能为空.");
		}
		if (StringManage.isEmpty(password)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"密码 不能为空.");
		}
		if (StringManage.isEmpty(url)) {
			throw new DataBaseException(DataBaseException.FS_NULL_FIELD_ERROR,
					"链接地址不能为空 .");
		}
	}
}