/**
 * @(#)LevelSearch.java    2015年3月13日
 *
 * Copyright 2015 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.lyq.jkxtest.framework;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 等级查询条件对象
 * 
 * @author wangchao
 * 
 * @version 2015年3月13日
 * 
 * @since JDK 1.6
 * 
 */
public class LevelSearch implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String accreditId = StringManage.FS_EMPTY;// 授权编号
	protected int productType = 0;// 类型(0:标准版;1:通用版(有流程);2:通用版(无流程))
	protected String beginTime = StringManage.FS_EMPTY;// 开始时间
	protected String endTime = StringManage.FS_EMPTY;// 结束时间
	protected int departmentId = 0;// 分公司编号
	protected String followMan = StringManage.FS_EMPTY;// 跟单员
	protected String departmentSql=StringManage.FS_EMPTY;//多个部门时拼接部门sql语句

	public String getAccreditId() {
		return accreditId;
	}

	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getFollowMan() {
		return followMan;
	}

	public void setFollowMan(String followMan) {
		this.followMan = followMan;
	}

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public String getDepartmentSql() {
		return departmentSql;
	}

	public void setDepartmentSql(String departmentSql) {
		this.departmentSql = departmentSql;
	}

	public void clone(LevelSearch obj) {
		if (obj != null) {
			obj.accreditId = accreditId;
			obj.productType = productType;
			obj.beginTime = beginTime;
			obj.endTime = endTime;
			obj.departmentId = departmentId;
			obj.followMan = followMan;
			obj.departmentSql = departmentSql;
		}
	}

}
