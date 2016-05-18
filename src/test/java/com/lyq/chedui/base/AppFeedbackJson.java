/**
 * @(#)AppFeedbackVO.java     2016-5-13
 *
 * Copyright 2014 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.lyq.chedui.base;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 意见反馈扩展Json对象
 * 
 * @author liyunqiang
 *
 * @version 2016年5月13日
 *
 */
public class AppFeedbackJson implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String accreditId = StringManage.FS_EMPTY;// 授权编号
	protected String contents = StringManage.FS_EMPTY;// 意见内容
	protected String userId = StringManage.FS_EMPTY;// 用户名
	protected String uniqueKey = StringManage.FS_EMPTY;// 唯一标识
	protected String mobilePhoneInfo = StringManage.FS_EMPTY;// 手机信息
	protected String version = StringManage.FS_EMPTY;// 版本号
	protected String password = StringManage.FS_EMPTY;// 密码
	protected String methodname = StringManage.FS_EMPTY;// 方法名
	protected String imageFormat = StringManage.FS_EMPTY;// 图片格式
	protected String[] imageStreams = new String[0];// 图片流数组

	public String getAccreditId() {
		return accreditId;
	}

	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public String getMobilePhoneInfo() {
		return mobilePhoneInfo;
	}

	public void setMobilePhoneInfo(String mobilePhoneInfo) {
		this.mobilePhoneInfo = mobilePhoneInfo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

	public String getImageFormat() {
		return imageFormat;
	}

	public void setImageFormat(String imageFormat) {
		this.imageFormat = imageFormat;
	}

	public String[] getImageStreams() {
		return imageStreams;
	}

	public void setImageStreams(String[] imageStreams) {
		this.imageStreams = imageStreams;
	}

}
