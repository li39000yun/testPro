/**
 * @(#)UserInfo.java     2010-2-24
 *
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.common;

import java.util.HashMap;

import com.kingsoft.control.AbstractUser;
import com.kingsoft.control.util.StringManage;
import com.kingsoft.dao.entity.system.User;

/**
 * 当前用户相关信息
 * 
 * @author zhangxulong
 * 
 * @version 2010-2-24
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */
public class UserInfo extends AbstractUser {

	private static final long serialVersionUID = 1L;
	private String accreditId = StringManage.FS_EMPTY;// 所属授权编号
	private User user = new User();// 用户个人信息
	/**
	 * 用户的操作级别 0:普通,操作自己相关信息. 1:中等,操作本公司相关信息. 2:高级,操作系统所有信息.
	 */
	private int level = 0;

	HashMap<String, String> rights = new HashMap<String, String>();// 用户权限信息

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public HashMap<String, String> getRights() {
		return rights;
	}

	public void setRights(HashMap<String, String> rights) {
		this.rights = rights;
	}

	public String getAccreditId() {
		return accreditId;
	}

	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
