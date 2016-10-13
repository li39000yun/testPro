/**
 * @(#)WeixinSearch.java    2015年4月23日
 *
 * Copyright 2015 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.lyq.jkxtest.framework;

import com.kingsoft.control.util.StringManage;

/**
 * 微信查询条件
 * 
 * @author wangchao
 * 
 * @version 2015年4月23日
 * 
 * @since JDK 1.6
 * 
 */
public class WeixinSearch extends LevelSearch {
	private static final long serialVersionUID = 1L;
	protected String loginId = StringManage.FS_EMPTY;// 登录帐号
	protected String paramString = StringManage.FS_EMPTY;// 查询参数
	protected int customerId = 0;// 客户编号
	protected String customerName = StringManage.FS_EMPTY;// 客户名称
	protected String customerContact = StringManage.FS_EMPTY;// 客户派单员
	protected String pilePlace = StringManage.FS_EMPTY;// 堆场地点

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getParamString() {
		return paramString;
	}

	public void setParamString(String paramString) {
		this.paramString = paramString;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public String getPilePlace() {
		return pilePlace;
	}

	public void setPilePlace(String pilePlace) {
		this.pilePlace = pilePlace;
	}

}
