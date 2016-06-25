package com.lyq.chedui.base;

import java.io.Serializable;

import com.kingsoft.control.util.StringManage;

/**
 * 接口基础
 * 
 * @author liyunqiang
 * @version 2016年2月17日
 */
public class CdbBase implements Serializable {
	private static final long serialVersionUID = 1L;
	protected String accreditId = StringManage.FS_EMPTY;// 授权编号
	protected String userId = StringManage.FS_EMPTY;// 用户名
	protected String password = StringManage.FS_EMPTY;// 密码
	protected String methodname = StringManage.FS_EMPTY;// 方法名

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
		this.userId = userId;
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

}
